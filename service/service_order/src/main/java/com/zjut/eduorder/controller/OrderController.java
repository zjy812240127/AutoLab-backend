package com.zjut.eduorder.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zjut.commonutils.JwtUtils;
import com.zjut.commonutils.R;
import com.zjut.eduorder.entity.Order;
import com.zjut.eduorder.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 订单 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-07-23
 */
@RestController
@RequestMapping("/eduorder/order")
//@CrossOrigin  // 使用spring cloud gateway时此处不能加注解，会造成二次跨域
public class OrderController {

    @Autowired
    private OrderService orderService;

    // 根据课程id以及会员id创建订单信息,返回订单id
    @ApiOperation(value = "根据课程id创建订单信息")
    @GetMapping("/newOrder/{courseId}")
    public R newOrder(
            @ApiParam(name = "courseId",value = "前端传递的课程id", required = true)
            @PathVariable String courseId,
            HttpServletRequest request){
        System.out.println("根据课程id创建订单信息==================");
        String orderId = orderService.createOrderByCourseId(courseId, JwtUtils.getMemberIdByJwtToken(request));
        return R.ok().data("orderId",orderId);
    }

    @ApiOperation(value = "根据订单编号获取当前订单信息")
    @GetMapping("/getOrderInfoById/{orderNo}")
    public R getOrderInfoById(@PathVariable String orderNo){
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("order_no",orderNo);
        Order order = orderService.getOne(wrapper);
        System.out.println("根据订单编号获取当前订单信息，订单的orderNo为：" + order.getOrderNo());
        return R.ok().data("orderInfo",order);
    }

    // 根据用户id和课程id查询该用户是否已经购买过该课程
    @ApiOperation(value = "根据用户id和课程id查询该用户是否已经购买过该课程")
    @GetMapping("/confirmOrder/{memberId}/{courseId}")
    public boolean confirmOrder(
            @ApiParam(name = "memberId",value = "要查询的用户id",required = true)
            @PathVariable String memberId,
            @ApiParam(name = "courseId",value = "要查询的课程id", required = true)
            @PathVariable String courseId){

        boolean flag = orderService.confirmOrder(memberId,courseId);
        // 前端根据该flag判断用户是否已经购买过该课程
        return flag;

    }
}

