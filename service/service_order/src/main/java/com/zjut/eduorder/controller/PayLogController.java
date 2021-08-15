package com.zjut.eduorder.controller;


import com.zjut.commonutils.R;
import com.zjut.eduorder.service.PayLogService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 支付日志表 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-07-23
 */
@RestController
@RequestMapping("/eduorder/paylog")
//@CrossOrigin  // 使用spring cloud gateway时此处不能加注解，会造成二次跨域
public class PayLogController {

    @Autowired
    private PayLogService payLogService;

    @ApiOperation(value = "根据订单id生成支付二维码")
    @GetMapping("/createNative/{orderNo}")
    public R createNative(@PathVariable String orderNo){
        // 除了要返回二维码，还要返回其他信息，所以放在map中传递
        System.out.println("后端收到的订单号orderNo：" + orderNo);
        Map map = payLogService.createNative(orderNo);
        return R.ok().data(map);

    }

    @ApiOperation(value = "根据订单id获取支付状态")
    @GetMapping("/queryPayStatus/{orderNo}")
    public R queryPayStatus(@PathVariable String orderNo){
        // 查询订单信息，返回map对象封装信息
        Map<String,String> map = payLogService.queryPayStatus(orderNo);
        //System.out.println("查询订单支付状态时前端传过来的订单编号为：" + orderNo);
        if (null == map){
            return R.error().message("支付出错");
        }
        // 固定写法，微信会返回这个trade_state字段
        //System.out.println("获取支付状态============" + map.get("trade_state"));
        if (map.get("trade_state").equals("SUCCESS")){
            // 支付成功更新订单表以及添加一条支付表的记录
            payLogService.updatePayStatus(map);
            return R.ok().message("支付成功");
        }
        return R.ok().message("支付中");



    }
}

