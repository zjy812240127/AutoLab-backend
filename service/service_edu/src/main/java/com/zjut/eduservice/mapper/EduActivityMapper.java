package com.zjut.eduservice.mapper;

import com.zjut.eduservice.entity.EduActivity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author testjava
 * @since 2021-08-10
 */
public interface EduActivityMapper extends BaseMapper<EduActivity> {

    List<EduActivity> getHotActivity();
}
