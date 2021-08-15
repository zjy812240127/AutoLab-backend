package com.zjut.eduservice.mapper;

import com.zjut.eduservice.entity.EduTeacher;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 讲师 Mapper 接口
 * </p>
 *
 * @author testjava
 * @since 2021-06-27
 */
public interface EduTeacherMapper extends BaseMapper<EduTeacher> {

    List<EduTeacher> getTeacherList();

}
