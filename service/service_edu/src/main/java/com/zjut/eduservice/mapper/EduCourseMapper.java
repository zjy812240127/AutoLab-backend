package com.zjut.eduservice.mapper;

import com.zjut.eduservice.entity.EduCourse;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zjut.eduservice.entity.vo.CoursePublishVo;
import com.zjut.eduservice.entity.vo.CourseWebVo;

import java.util.List;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author testjava
 * @since 2021-07-02
 */
public interface EduCourseMapper extends BaseMapper<EduCourse> {
    // 根据课程id查询课程发布信息
    CoursePublishVo getPublishCourseInfo(String courseId);

    CourseWebVo getCourseInfoById(String courseId);

    List<EduCourse> getHotCourses();

}
