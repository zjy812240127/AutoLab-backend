package com.zjut.eduservice.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zjut.eduservice.entity.EduCourse;
import com.zjut.eduservice.entity.EduTeacher;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author testjava
 * @since 2021-06-27
 */
public interface EduTeacherService extends IService<EduTeacher> {

    Map<String, Object> pageListWeb(Page<EduTeacher> pageTeacher);


    List<EduTeacher> getOlds();

}
