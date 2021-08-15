package com.zjut.eduorder.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zjut.commonutils.EduCourseOrder;
import com.zjut.commonutils.UcenterMemberOrder;
import com.zjut.eduorder.client.EduClient;
import com.zjut.eduorder.client.UcenterClient;
import com.zjut.eduorder.entity.Order;
import com.zjut.eduorder.mapper.OrderMapper;
import com.zjut.eduorder.service.OrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zjut.eduorder.utils.OrderNoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2021-07-23
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @Autowired
    private EduClient eduClient;
    @Autowired
    private UcenterClient ucenterClient;

    @Override
    public String createOrderByCourseId(String courseId, String memberIdByJwtToken) {

        Order order = new Order();
        // 远程调用edu模块根据课程id获取课程信息
        EduCourseOrder eduCourseOrder = eduClient.getCourseById(courseId);
        // 远程调用ucenter模块根据用户id获取用户信息
        System.out.println("用户id=" + memberIdByJwtToken);
        UcenterMemberOrder ucenterMemberOrder = ucenterClient.getUcenterOrder(memberIdByJwtToken);
        // 添加订单类中的其余信息
        order.setTotalFee(eduCourseOrder.getPrice());
        order.setTeacherName("test");
        order.setStatus(0);  // 未付款
        order.setPayType(1);  // 微信支付
        order.setOrderNo(OrderNoUtil.getOrderNo());
        order.setNickname(ucenterMemberOrder.getNickname());
        order.setMobile(ucenterMemberOrder.getMobile());
        order.setMemberId(memberIdByJwtToken);
        order.setCourseTitle(eduCourseOrder.getTitle());
        order.setCourseId(courseId);
        order.setCourseCover(eduCourseOrder.getCover());
        // 向订单数据库插入一条记录
        baseMapper.insert(order);
        // 返回订单id
        return order.getOrderNo();

    }

    @Override
    public boolean confirmOrder(String memberId, String courseId) {
        // 查询order表的字段status，如果为1表示已购买，返回true，否则返回false
        int orderStatus = 1;
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("member_id",memberId);
        wrapper.eq("course_id",courseId);
        wrapper.eq("status",orderStatus);
        System.out.println("查询用户是否已经购买过该课程：" + "memberId=" + memberId + "courseId=" + courseId + "status=" + orderStatus);
        Integer count = baseMapper.selectCount(wrapper);
        System.out.println("是否已经购买课程:" + count);
        if (count > 0){
            return true;
        }else {
            return false;
        }

    }
}
