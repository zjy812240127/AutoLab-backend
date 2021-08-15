package com.zjut.loginservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zjut.loginservice.entity.EduLogin;
import com.zjut.loginservice.entity.LoginVo;
import com.zjut.loginservice.entity.MemberRegister;
import com.zjut.loginservice.mapper.EduLoginMapper;
import com.zjut.loginservice.service.EduLoginService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zjut.loginservice.utils.RandomUtil;
import com.zjut.serviceBase.exceptionHandler.MyException;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.omg.CORBA.TRANSACTION_MODE;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.concurrent.TimeUnit;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author testjava
 * @since 2021-07-23
 */
@Service
public class EduLoginServiceImpl extends ServiceImpl<EduLoginMapper, EduLogin> implements EduLoginService {

    @Value("${email.sendEmailAddress}")
    private String sendEmailAddress;
    @Value("${email.smtpPort}")
    private int smtpPort;
    @Value("${email.fromName}")
    private String nickName;
    @Value("${email.authurationCode}")
    private String authCode;
    @Value("${email.head}")
    private String head;
    @Value("${email.sendhost}")
    private String emailSendhost;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public EduLogin selectMobile(String mobile) {

        QueryWrapper<EduLogin> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile", mobile);

        EduLogin eduLogin = baseMapper.selectOne(wrapper);

        return eduLogin;
    }

    @Override
    public Boolean sendEmail(String address) {

        // 六位随机验证码
        String code = RandomUtil.getSixBitRandom();
        HtmlEmail send = new HtmlEmail();
        try {
            send.setHostName("smtp.126.com");
            send.setCharset("utf-8");
            send.setFrom("zjuter8122@126.com", "zhangsan");  //第一个参数是发送者的QQEamil   第二个参数是发送者QQ昵称
            send.setAuthentication("zjuter8122@126.com", "TVJXRHFCHRRGYQTP"); //第一个参数是发送者的QQEamil   第二个参数是刚刚获取的授权码
            send.addTo(address); // 接收者的邮箱
            send.setSubject("teng给您送上验证码");//Eamil的标题
            send.setMsg("欢迎光临，特此送上验证:   " + code + "   请您签收");  //Eamil的内容
            send.setSmtpPort(25);  // QQ邮箱第三方使用的端口
            //send.setSSLCheckServerIdentity(true);  // 开启SSL加密
            send.send(); //发送
        } catch (EmailException e) {
            e.printStackTrace();
        }
        // 发送成功将验证码加入redis
        redisTemplate.opsForValue().set("code",code,30, TimeUnit.MINUTES);

        return new Boolean(true);


    }

    @Override
    public Boolean register(MemberRegister memberRegister) {

        // 从redis验证验证码是否过期以及正确
        String code = memberRegister.getCode();
        System.out.println("进入server层===============");
        String redisCode = (String) redisTemplate.opsForValue().get("code");
        if (StringUtils.isEmpty(redisCode)){
            throw new MyException(20001,"验证码已经过期");
        }
        if (redisCode != code){
            throw new MyException(20001,"验证码错误");
        }
        EduLogin eduLogin = new EduLogin();

        BeanUtils.copyProperties(memberRegister,eduLogin);
        int insert = baseMapper.insert(eduLogin);
        if (insert >0){
            // 加入数据库成功，删除缓存中的数据
            redisTemplate.delete("code");
        }
        return new Boolean(true);


    }

    @Override
    public LoginVo getLoginInfo(String memberId) {
        EduLogin loginMember = baseMapper.selectById(memberId);

        LoginVo loginVo = new LoginVo();
        BeanUtils.copyProperties(loginMember,loginVo);
        return loginVo;

    }
}
