package com.zjut.eduservice.client;

import com.zjut.commonutils.R;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient("service-order")
public interface OrderClient {
    // 根据用户id和课程id查询该用户是否已经购买过该课程
    @ApiOperation(value = "根据用户id和课程id查询该用户是否已经购买过该课程")
    @GetMapping("/eduorder/order/confirmOrder/{memberId}/{courseId}")
    public boolean confirmOrder(
            @ApiParam(name = "memberId",value = "要查询的用户id",required = true)
            @PathVariable("memberId") String memberId,
            @ApiParam(name = "courseId",value = "要查询的课程id", required = true)
            @PathVariable("courseId") String courseId);
    }
