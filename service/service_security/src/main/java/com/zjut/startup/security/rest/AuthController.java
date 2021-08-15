package com.zjut.startup.security.rest;

import com.zjut.startup.security.utils.JwtTokenUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@Api("授权登录")
@Slf4j
@RestController
@RequestMapping("/edulogin/auth")
public class AuthController {
    private final JwtTokenUtils jwtTokenUtils;

    public AuthController(JwtTokenUtils jwtTokenUtils) {
        this.jwtTokenUtils = jwtTokenUtils;
    }

    @ApiOperation("登录授权")
    @GetMapping(value = "/login")
    public String login(String user,String password){
        Map map = new HashMap();
        map.put("user",user);
        map.put("password",password);
        return jwtTokenUtils.createToken(map);
    }


}
