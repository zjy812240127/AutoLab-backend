package com.zjut.eduorder.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.nacos.client.utils.JSONUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.wxpay.sdk.WXPayUtil;
import com.zjut.commonutils.R;
import com.zjut.eduorder.entity.Order;
import com.zjut.eduorder.entity.PayLog;
import com.zjut.eduorder.mapper.PayLogMapper;
import com.zjut.eduorder.service.OrderService;
import com.zjut.eduorder.service.PayLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zjut.eduorder.utils.HttpClient;
import com.zjut.serviceBase.exceptionHandler.MyException;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 支付日志表 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2021-07-23
 */
@Service
public class PayLogServiceImpl extends ServiceImpl<PayLogMapper, PayLog> implements PayLogService {

    @Autowired
    private OrderService orderService;
    @Autowired
    private PayLogService payLogService;

    @Override
    public Map createNative(String orderNo) {

        try {
            // 根据订单id获取订单信息
            QueryWrapper<Order> wrapper = new QueryWrapper<>();
            wrapper.eq("order_no",orderNo);
            System.out.println("前端传递的order_no为：" + orderNo);
            Order order = orderService.getOne(wrapper);
            System.out.println("根据订单号查到的订单为：" + order);
            // 根据订单信息设置支付信息
            Map payMap = new HashMap();
            // 需要企业申请权限，使用guli提供的信息
            payMap.put("appid","wx74862e0dfcf69954");
            payMap.put("mch_id","1558950191");
            payMap.put("nonce_str", WXPayUtil.generateNonceStr());
            payMap.put("body",order.getCourseTitle());  // 课程标题
            payMap.put("out_trade_no",orderNo);  // 订单号，二维码的唯一标识
            payMap.put("total_fee",order.getTotalFee().multiply(new BigDecimal("100")).longValue()+"");  // 金额转换，并转换成字符串
            payMap.put("spbill_create_ip", "127.0.0.1");
            payMap.put("notify_url",
                    "http://guli.shop/api/order/weixinPay/weixinNotify\n");  // 支付完成后的回调地址
            payMap.put("trade_type", "NATIVE");  // 二维码的支付类型

            // 建立第三方url地址链接，并放入支付参数，参数需要转换成xml格式
            HttpClient client = new HttpClient("https://api.mch.weixin.qq.com/pay/unifiedorder");  // 微信支付固定地址

            // client设置参数xml,key写的是guli提供的商户信息
            client.setXmlParam(WXPayUtil.generateSignedXml(payMap, "T6m9iK73b0kn9g5v426MKfHQH7X8rKwb"));
            client.setHttps(true);  // 开启支持https访问
            client.post();
            // 返回第三方数据，返回的是xml格式的参数，需要转换成map型
            String xml = client.getContent();
            Map<String, String> resultMap = WXPayUtil.xmlToMap(xml);
            // 封装返回结果并将其放入map中，返回map，resultMap里面的参数不够，重新建立一个map存入其他前端需要的数据
            Map map = new HashMap<>();
            map.put("out_trade_no", orderNo);
            map.put("course_id", order.getCourseId());
            map.put("total_fee", order.getTotalFee());
            map.put("result_code", resultMap.get("result_code"));
            map.put("code_url", resultMap.get("code_url"));  // 二维码页面地址
            return map;

        } catch (Exception e) {
            throw new MyException(20001,"创建二维码失败");
        }
    }

    // 查询订单支付信息，不是订单信息，需要通过微信查
    @Override
    public Map<String, String> queryPayStatus(String orderNo) {

        try {
            // 查询订单状态，格式是固定的，通过微信进行查询
            // 1.封装数据

            System.out.println("查询订单状态===============");
            Map queryMap = new HashMap();
            queryMap.put("appid", "wx74862e0dfcf69954");
            queryMap.put("mch_id", "1558950191");
            queryMap.put("out_trade_no", orderNo);
            queryMap.put("nonce_str", WXPayUtil.generateNonceStr());
            // 2.向微信查询
            HttpClient client = new HttpClient("https://api.mch.weixin.qq.com/pay/orderquery");
            client.setXmlParam(WXPayUtil.generateSignedXml(queryMap, "T6m9iK73b0kn9g5v426MKfHQH7X8rKwb"));
            client.setHttps(true);
            client.post();
            // 3. 返回第三方数据
            // 4.转换成map格式的数据
            String xml = client.getContent();
            Map<String, String> resultMap = WXPayUtil.xmlToMap(xml);
            // 5. 返回
            return resultMap;
        }catch (Exception e){
            throw new MyException(20001,"查询订单状态失败");
        }


    }

    @Override
    public void updatePayStatus(Map<String, String> map) {
        String orderNo = map.get("out_trade_no");
        // 根据订单编号获取订单信息
        //System.out.println("查询订单支付状态========");
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("order_no",orderNo);
        System.out.println("订单编号为：" + orderNo);
        Order order = orderService.getOne(wrapper);
        // 如果表中已经为完成支付状态就不再更新
        if (order.getStatus().intValue() == 1) return;
        order.setStatus(1);  // 设为已支付
        boolean update = orderService.updateById(order);

        // 支付表中新增一条记录
        PayLog payLog = new PayLog();
        payLog.setOrderNo(orderNo);
        payLog.setTransactionId(map.get("transaction_id"));
        payLog.setTradeState(map.get("trade_state"));
        payLog.setTotalFee(order.getTotalFee());
        payLog.setPayType(1);
        payLog.setPayTime(new Date());
        payLog.setAttr(JSONObject.toJSONString(map));  // 其他属性，转换成json进行存储
        baseMapper.insert(payLog);

    }


}
