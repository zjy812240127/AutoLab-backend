package com.zjut.staservice.client;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient("service-ucenter")
public interface UcenterClient {

    @ApiOperation(value = "统计当天注册的人数")
    @GetMapping("/eduuct/apimember/registerNum/{day}")
    public int registerNum(
            @ApiParam(name = "day",value = "要统计注册人数的日期", required = true)
            @PathVariable("day") String day);

}
