package com.zjut.loginservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zjut.commonutils.JwtUtils;
import com.zjut.commonutils.R;
import com.zjut.loginservice.entity.EduLogin;
import com.zjut.loginservice.entity.MemberRegister;
import com.zjut.loginservice.service.EduLoginService;
import com.zjut.loginservice.utils.RandomUtil;
import com.zjut.serviceBase.exceptionHandler.MyException;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-07-23
 */
@RestController
@RequestMapping("/loginservice/login")
//@CrossOrigin  // 使用spring cloud gateway时此处不能加注解，会造成二次跨域
public class EduLoginController {

    @Autowired
    private EduLoginService eduLoginService;


    @ApiOperation(value = "会员简单登录")
    @PostMapping("/{mobile}/{password}")
    public R login(
            @ApiParam(name = "mobile",value = "会员手机号",required = true)
            @PathVariable String mobile,
            @ApiParam(name = "password", value = "会员密码" , required = true)
            @PathVariable String password){
        Boolean flag = false;
        // 查询该手机再数据库中是否存在，不存在则报错
        EduLogin eduLogin  = eduLoginService.selectMobile(mobile);
        if (null == eduLogin){
            throw new MyException(20001,"请先注册成为会员");
        }else {
            // 存在就查询对应记录的密码与前端传入的是否一致
            System.out.println("前端的password=" + password + "数据库查询的password = " + eduLogin.getPassword());
            if (!password.equals(eduLogin.getPassword())){
                throw new MyException(20001,"请输入正确的密码");
            }else {
                // 账号密码正确，前端获取到的flag是true则跳转到管理页面，进行网页资源增删管理（该项目的前半部分，端口号不同，还是可以通过nginx跳转）
                flag = true;
                return R.ok().message("尊敬的会员您好，欢迎登录！").data("flag",flag);
            }
        }
    }

    @ApiOperation(value = "管理员登录返回token并跳转到页面")
    @GetMapping("/GetToken/{mobile}/{password}")
    public R adminLogin(
            @ApiParam(name = "mobile",value = "会员手机号",required = true)
            @PathVariable String mobile,
            @ApiParam(name = "password", value = "会员密码" , required = true)
            @PathVariable String password){
        Boolean flag = false;
        // 查询该手机再数据库中是否存在，不存在则报错
        EduLogin eduLogin  = eduLoginService.selectMobile(mobile);
        if (null == eduLogin){
            throw new MyException(20001,"请先注册成为会员");
        }else {
            // 存在就查询对应记录的密码与前端传入的是否一致
            System.out.println("前端的password=" + password + "数据库查询的password = " + eduLogin.getPassword());
            if (!password.equals(eduLogin.getPassword())){
                throw new MyException(20001,"请输入正确的密码");
            }else {
                // 账号密码正确，前端获取到的flag是true则跳转到管理页面，进行网页资源增删管理（该项目的前半部分，端口号不同，还是可以通过nginx跳转）
                flag = true;
                String jwtToken = JwtUtils.getJwtToken(eduLogin.getId(), eduLogin.getName());
                return R.ok().message("尊敬的会员您好，欢迎登录！").data("flag",flag).data("token",jwtToken);
            }
        }
    }

    @ApiOperation(value = "普通用户注册时获取邮箱验证码")
    @PostMapping("/memberRegister/email")
    public R memberRegiter(
            @RequestBody MemberRegister memberRegister
            ){
        /**
         * 1.生成随机验证码
         * 2.调用接口用我的邮箱向对方邮箱发送验证码
         * 3.将验证码放入redis并设置时间为30分钟
         * 4.注册时从redis获取验证码，并比较是否一致
         */
        String address = memberRegister.getEmail();
        System.out.println("获取验证码====目标邮箱为：" + address);
        if (StringUtils.isEmpty(address)){
            throw new MyException(20001,"请输入邮箱");
        }
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("email",address);
        // 查询该邮箱是否已被注册
        int count = eduLoginService.count(wrapper);
        if (count > 0){
            throw new MyException(20001,"改邮箱已被注册");
        }
        Boolean flag = eduLoginService.sendEmail(address);
        if (flag == false){
            return R.error().message("发送验证码失败");
        }else {
            return R.ok().message("发送验证码成功");
        }

    }



    @ApiOperation(value = "普通用户注册")
    @PostMapping("/newRegister")
    public R newRegister(@RequestBody MemberRegister memberRegister){
        // 检查前端发送的数据是否齐全
        String nickName = memberRegister.getName();
        String password = memberRegister.getPassword();
        String email = memberRegister.getEmail();
        String code = memberRegister.getCode();
        if (StringUtils.isEmpty(nickName) || StringUtils.isEmpty(password) || StringUtils.isEmpty(email) || StringUtils.isEmpty(code)){
            throw new MyException(20001,"请输入完整的用户信息");
        }
        System.out.println("执行注册操作==========================");
        Boolean flag = eduLoginService.register(memberRegister);
        if (flag == false){
            throw new MyException(20001,"注册失败");
        }else {
            return R.ok().message("注册成功");
        }


    }



}

