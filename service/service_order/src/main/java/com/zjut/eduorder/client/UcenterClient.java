package com.zjut.eduorder.client;

import com.zjut.commonutils.UcenterMemberOrder;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Component
@FeignClient("service-ucenter")
public interface UcenterClient {

    @ApiOperation(value = "创建订单时根据用户id查询用户信息")
    @GetMapping("/eduuct/apimember/getUcenterMemberOrder/{id}")
    public UcenterMemberOrder getUcenterOrder(@PathVariable String id);


    }
