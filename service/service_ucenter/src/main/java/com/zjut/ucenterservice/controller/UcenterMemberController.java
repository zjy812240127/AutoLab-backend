package com.zjut.ucenterservice.controller;


import com.zjut.commonutils.R;
import com.zjut.commonutils.UcenterMemberOrder;
import com.zjut.serviceBase.exceptionHandler.MyException;
import com.zjut.ucenterservice.entity.UcenterMember;
import com.zjut.ucenterservice.entity.vo.LoginVo;
import com.zjut.ucenterservice.entity.vo.RigisterVo;
import com.zjut.ucenterservice.service.UcenterMemberService;
import com.zjut.ucenterservice.utils.JwtUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-07-20
 */
@RestController
@RequestMapping("/eduuct/apimember")
//@CrossOrigin  // 使用spring cloud gateway时此处不能加注解，会造成二次跨域
public class UcenterMemberController {

    @Autowired
    private UcenterMemberService ucenterMemberService;

    @ApiOperation(value = "会员登录")
    @PostMapping("/login")
    public R login(@RequestBody LoginVo loginVo){
        String token = ucenterMemberService.login(loginVo);
        return R.ok().data("token",token);
    }

    @ApiOperation(value = "会员注册")
    @PostMapping("/register")
    public R register(@RequestBody RigisterVo rigisterVo){
        ucenterMemberService.rigister(rigisterVo);
        return R.ok();
    }

    //@ApiOperation(value = "根据token获取用户信息")
    //@GetMapping(value = "/getLoginInfo")
    //public R getLoginInfo(HttpServletRequest request){
    //    String id = JwtUtils.getMemberIdByJwtToken(request);
    //    UcenterMember ucenterMember = ucenterMemberService.getById(id);
    //    return R.ok().data("LoginInfo",ucenterMember);
    //}

    @ApiOperation(value = "根据token获取登录信息")
    @GetMapping("/getLoginInfo")
    public R getLoginInfo(HttpServletRequest request){
        try {
            //System.out.println("前端访问的request为："+request);
            String memberId = JwtUtils.getMemberIdByJwtToken(request);
            //System.out.println("根据token得到的用户id=" + memberId);
            LoginVo loginVo = ucenterMemberService.getLoginInfo(memberId);
            //System.out.println("登录的用户的信息为：" + loginVo);
            return R.ok().data("items",loginVo);
        }catch (Exception e){
            throw new MyException(20001,"根据token获取登录信息失败");
        }

    }

    @ApiOperation(value = "创建订单时根据用户id查询用户信息")
    @GetMapping("/getUcenterMemberOrder/{id}")
    public UcenterMemberOrder getUcenterOrder(@PathVariable String id){

        UcenterMember ucenterMember = ucenterMemberService.getById(id);
        UcenterMemberOrder ucenterMemberOrder = new UcenterMemberOrder();
        BeanUtils.copyProperties(ucenterMember,ucenterMemberOrder);
        return ucenterMemberOrder;
    }

    @ApiOperation(value = "统计当天注册的人数")
    @GetMapping("/registerNum/{day}")
    public int registerNum(
            @ApiParam(name = "day",value = "要统计注册人数的日期", required = true)
            @PathVariable String day){
        int count = ucenterMemberService.registerNum(day);
        return count;
    }

    @ApiOperation(value = "根据用户id查询用户信息")
    @GetMapping("/getUserInfoById/{userId}")
    public UcenterMemberOrder getUserInfo(
            @ApiParam(name = "userId", value = "用户id", required = true)
            @PathVariable String userId){
        UcenterMember ucenterMember = ucenterMemberService.getById(userId);
        UcenterMemberOrder commonUser = new UcenterMemberOrder();
        BeanUtils.copyProperties(ucenterMember,commonUser);
        return commonUser;
    }

}

