package com.zjut.eduservice.controller;

import com.zjut.commonutils.R;
import org.springframework.web.bind.annotation.*;

/**
 * @author Joya Zou
 * @date 2021年06月29日 10:40
 */
@RestController
@RequestMapping("/eduservice/user")
//@CrossOrigin  // 使用spring cloud gateway时此处不能加注解，会造成二次跨域
public class EduLoginController {
    /**
     * 登录时要调用的两个方法
     */

    // login
    @PostMapping("/login")
    public R login(){

        // 根据前端页面的返回值要求来写
        return R.ok().data("token","admin");
    }

    // info
    @GetMapping("/info")
    public R info(){
        // avatar: 头像
        return R.ok().data("roles","[admin]").data("name","admin").data("avatar","https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
    }


}
