package com.zjut.ucenterservice.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import com.zjut.serviceBase.exceptionHandler.MyException;
import com.zjut.ucenterservice.entity.UcenterMember;
import com.zjut.ucenterservice.entity.vo.LoginVo;
import com.zjut.ucenterservice.entity.vo.RigisterVo;
import com.zjut.ucenterservice.mapper.UcenterMemberMapper;
import com.zjut.ucenterservice.service.UcenterMemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zjut.ucenterservice.utils.JwtUtils;
import com.zjut.ucenterservice.utils.MD5;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2021-07-20
 */
@Service
public class UcenterMemberServiceImpl extends ServiceImpl<UcenterMemberMapper, UcenterMember> implements UcenterMemberService {

    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private UcenterMemberMapper ucenterMemberMapper;

    @Override
    public String login(LoginVo loginVo) {
        String mobile = loginVo.getMobile();
        String password = loginVo.getPassword();

        // 校验是否输入了电话以及密码
        if(StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password)){
            throw new MyException(20001,"请输入账号密码");
        }

        // 根据账号查询数据库中是否存在会员信息
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("mobile",mobile);
        UcenterMember member = baseMapper.selectOne(wrapper);
        if(null == member){
            throw new MyException(20001,"请先注册");
        }

        // 存在会员信息，对比密码是否正确
        if(! MD5.encrypt(password).equals(member.getPassword())){
            throw new MyException(20001,"密码错误");
        }

        // 校验账号是否被禁用
        if(member.getIsDisabled()){
            throw new MyException(20001,"账号已被禁用");
        }

        // 密码正确生成token返回给前端
        //System.out.println("用户登录时用于生成token的用户id=" + member.getId());
        //System.out.println("用户登录时用于生成token的用户nikeName=" + member.getNickname());
        String token = JwtUtils.getJwtToken(member.getId(),member.getNickname());
        return token;
    }

    // 会员注册
    @Override
    public void rigister(RigisterVo rigisterVo) {
        // 获取用户输入信息
        String mobile = rigisterVo.getMobile();
        String nickName = rigisterVo.getNikeName();
        String password = rigisterVo.getPassword();
        String code = rigisterVo.getCode();

        // 检验数据是否输入完全
        if(StringUtils.isEmpty(mobile) || StringUtils.isEmpty(nickName) || StringUtils.isEmpty(password) || StringUtils.isEmpty(code) ){
            throw new MyException(20001,"请输入完整信息");
        }

        // 检验输入的验证码是否与redis中存储的一致
        if (!code.equals(redisTemplate.opsForValue().get(mobile))){
            throw new MyException(20001,"验证码错误");
        }

        // 检验数据库中是否存在相同的号码，防止重复注册
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("mobile",mobile);
        Integer count = baseMapper.selectCount(wrapper);
        if(count > 0){
            // 表示已经注册
            throw new MyException(20001,"已被注册");
        }

        // 将该用户信息存入数据库
        UcenterMember member = new UcenterMember();
        member.setPassword(password);
        member.setNickname(nickName);
        member.setIsDisabled(false);
        // 头像
        member.setAvatar("http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eoj0hHXhgJNOTSOFsS4uZs8x1ConecaVOB8eIl115xmJZcT4oCicvia7wMEufibKtTLqiaJeanU2Lpg3w/132");
        member.setMobile(mobile);

        baseMapper.insert(member);

    }

    @Override
    public UcenterMember getByOpenid(String openid) {

        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("openid",openid);
        UcenterMember member = baseMapper.selectOne(wrapper);
        return member;
    }

    @Override
    public LoginVo getLoginInfo(String memberId) {

        UcenterMember member = baseMapper.selectById(memberId);
        LoginVo loginVo = new LoginVo();
        BeanUtils.copyProperties(member,loginVo);
        return loginVo;
    }

    @Override
    public int registerNum(String day) {
        // 在sql语句中用DATE(gmt_create)将创建时间转化成精确到天
        int count = ucenterMemberMapper.registerCount(day);
        return count;

    }

}
