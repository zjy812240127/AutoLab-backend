package com.zjut.msmservice.controller;



import com.zjut.commonutils.R;
import com.zjut.msmservice.ServiceMsmApplication;
import com.zjut.msmservice.service.Msmservice;
import com.zjut.msmservice.utils.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;


@RestController
//@CrossOrigin  // 使用spring cloud gateway时此处不能加注解，会造成二次跨域
@RequestMapping("/edumsm")
public class MsmApiController {

    @Autowired
    private Msmservice msmservice;
    @Autowired
    private RedisTemplate<String,String> redeisTemplate;

    @GetMapping("/send/{phone}")
    public R code(@PathVariable String phone){

        // 通过在redis中设置code5分钟存活来设置验证码五分钟内有效，时间段内访问直接从redis读取，不需要再次生成验证码
        String code = redeisTemplate.opsForValue().get(phone);
        if(!StringUtils.isEmpty(code)){
            return R.ok();
        }

        // 生成四位数的验证码，通过阿里云发送给用户
        code = RandomUtil.getFourBitRandom();
        // 阿里云传参只支持json格式，map可以直接转换成json格式
        Map<String,Object> param = new HashMap<>();
        param.put("code",code);
        // "SMS_180051135"是自己开通的阿里云的模板的code，
        boolean isSend = msmservice.send(phone,"SMS_180051135",param);
        if(isSend){
            // 存入缓存包存5min
            redeisTemplate.opsForValue().set(phone,code,5, TimeUnit.MINUTES);
            return R.ok();
        }else {
            return R.error().message("短信发送失败");

        }
    }




}
