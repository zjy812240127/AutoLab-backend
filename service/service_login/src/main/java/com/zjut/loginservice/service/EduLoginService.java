package com.zjut.loginservice.service;

import com.zjut.loginservice.entity.EduLogin;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zjut.loginservice.entity.LoginVo;
import com.zjut.loginservice.entity.MemberRegister;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author testjava
 * @since 2021-07-23
 */
public interface EduLoginService extends IService<EduLogin> {

    EduLogin selectMobile(String mobile);

    Boolean sendEmail(String address);


    Boolean register(MemberRegister memberRegister);

    LoginVo getLoginInfo(String memberId);
}
