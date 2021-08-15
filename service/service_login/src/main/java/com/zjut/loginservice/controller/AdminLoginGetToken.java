package com.zjut.loginservice.controller;

import com.zjut.commonutils.JwtUtils;
import com.zjut.commonutils.R;
import com.zjut.loginservice.entity.EduLogin;
import com.zjut.loginservice.entity.LoginVo;
import com.zjut.loginservice.service.EduLoginService;
import com.zjut.serviceBase.exceptionHandler.MyException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.IOException;

@Api("管理员登录并获取token")
@Controller
//@CrossOrigin  // 使用spring cloud gateway时此处不能加注解，会造成二次跨域
@RequestMapping("/loginservice/adminLogin")
public class AdminLoginGetToken {

    @Autowired
    private EduLoginService eduLoginService;

    @ApiOperation(value = "管理员登录返回token并跳转到页面")
    @GetMapping("/GetToken/{mobile}/{password}")
    public void callback(
            @ApiParam(name = "mobile",value = "管理员手机号",required = true)
            @PathVariable String mobile,
            @ApiParam(name = "password", value = "管理员密码" , required = true)
            @PathVariable String password,
            HttpServletResponse httpServletResponse){

        // 查询该手机再数据库中是否存在，不存在则报错
        EduLogin eduLogin  = eduLoginService.selectMobile(mobile);
        if (null == eduLogin){
            throw new MyException(20001,"请先注册成为管理员");
        }else {
            // 存在就查询对应记录的密码与前端传入的是否一致
            System.out.println("前端的password=" + password + "数据库查询的password = " + eduLogin.getPassword());
            if (!password.equals(eduLogin.getPassword())){
                throw new MyException(20001,"请输入正确的密码");
            }else {
                // 账号密码正确，生成token并进行页面跳转
                String jwtToken = JwtUtils.getJwtToken(eduLogin.getId(), eduLogin.getName());
                //因为端口号不同存在跨域问题，cookie不能跨域，所以这里使用url重写
                //return "redirect:http://localhost:3000?token=" + jwtToken;
                //return "redirect:http://localhost:3000";
                String url = "http://www.baidu.com";
                try {
                    httpServletResponse.sendRedirect(url);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @ApiOperation(value = "根据token获取管理员登录信息")
    @GetMapping("/getLoginInfo")
    public R getLoginInfo(HttpServletRequest request){
        try {
            String memberId = JwtUtils.getMemberIdByJwtToken(request);
            System.out.println("根据token得到管理员id=" + memberId);
            LoginVo loginVo = eduLoginService.getLoginInfo(memberId);

            return R.ok().data("items",loginVo);
        }catch (Exception e){
            throw new MyException(20001,"根据token获取登录信息失败");
        }

    }



}
