package com.zjut.loginservice.mapper;

import com.zjut.loginservice.entity.EduLogin;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zjut.loginservice.entity.MemberRegister;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author testjava
 * @since 2021-07-23
 */
@Mapper
public interface EduLoginMapper extends BaseMapper<EduLogin> {

    int register(MemberRegister memberRegister);
}
