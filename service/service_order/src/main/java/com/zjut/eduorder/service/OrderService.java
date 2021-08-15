package com.zjut.eduorder.service;

import com.zjut.eduorder.entity.Order;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 订单 服务类
 * </p>
 *
 * @author testjava
 * @since 2021-07-23
 */
public interface OrderService extends IService<Order> {

    String createOrderByCourseId(String courseId, String memberIdByJwtToken);

    boolean confirmOrder(String memberId, String courseId);
}
