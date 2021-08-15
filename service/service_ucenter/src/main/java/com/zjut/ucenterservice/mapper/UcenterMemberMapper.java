package com.zjut.ucenterservice.mapper;

import com.zjut.ucenterservice.entity.UcenterMember;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 会员表 Mapper 接口
 * </p>
 *
 * @author testjava
 * @since 2021-07-20
 */
@Mapper
public interface UcenterMemberMapper extends BaseMapper<UcenterMember> {

    int registerCount(String day);
}
