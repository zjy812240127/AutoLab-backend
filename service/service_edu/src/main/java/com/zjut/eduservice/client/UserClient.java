package com.zjut.eduservice.client;

import com.zjut.commonutils.UcenterMemberOrder;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient("service-ucenter")
public interface UserClient {

    @ApiOperation(value = "根据用户id查询用户信息")
    @GetMapping("/eduuct/apimember/getUserInfoById/{userId}")
    public UcenterMemberOrder getUserInfo(
            @ApiParam(name = "userId", value = "用户id", required = true)
            @PathVariable("userId") String userId);
}
