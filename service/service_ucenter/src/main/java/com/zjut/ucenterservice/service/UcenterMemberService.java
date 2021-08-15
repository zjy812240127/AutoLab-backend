package com.zjut.ucenterservice.service;

import com.zjut.ucenterservice.entity.UcenterMember;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zjut.ucenterservice.entity.vo.LoginVo;
import com.zjut.ucenterservice.entity.vo.RigisterVo;

import java.util.Map;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author testjava
 * @since 2021-07-20
 */
public interface UcenterMemberService extends IService<UcenterMember> {

    String login(LoginVo loginVo);

    void rigister(RigisterVo rigisterVo);

    UcenterMember getByOpenid(String openid);

    LoginVo getLoginInfo(String memberId);

    int registerNum(String day);

}
