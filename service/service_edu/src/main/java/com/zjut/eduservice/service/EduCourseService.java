package com.zjut.eduservice.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zjut.eduservice.entity.EduCourse;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zjut.eduservice.entity.vo.AddCourseVo;
import com.zjut.eduservice.entity.vo.CoursePublishVo;
import com.zjut.eduservice.entity.vo.CourseQueryVo;
import com.zjut.eduservice.entity.vo.CourseWebVo;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author testjava
 * @since 2021-07-02
 */
public interface EduCourseService extends IService<EduCourse> {

  String  addCourse(AddCourseVo addCourseVo);

    AddCourseVo findById(String id);

    void updateCourse(AddCourseVo courseVo);

  CoursePublishVo getPublishInfo(String id);

    void deleteCourse(String id);

    CourseWebVo getCourseInfo(String courseId);

    void updatePageViewCount(String id);

    List<EduCourse> getListByTeacherId(String id);

    Map<String, Object> getCourseList(Page<EduCourse> coursePage, CourseQueryVo courseQueryVo);

    List<EduCourse> getHot();

}
