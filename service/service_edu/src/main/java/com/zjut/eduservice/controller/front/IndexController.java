package com.zjut.eduservice.controller.front;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zjut.commonutils.R;
import com.zjut.eduservice.entity.EduCourse;
import com.zjut.eduservice.entity.EduTeacher;
import com.zjut.eduservice.service.EduCourseService;
import com.zjut.eduservice.service.EduTeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api("查询首页热门的八门课程以及四位名师")
@RestController
//@CrossOrigin
@RequestMapping("/eduservice/index")
public class IndexController {

    @Autowired
    private EduTeacherService eduTeacherService;
    @Autowired
    private EduCourseService eduCourseService;

    @ApiOperation("查询热门的前四位名师和前把门课程")
    @GetMapping(value = "/getIndex")
    // 每次查询首页信息将数据写入缓存
    @Cacheable(value = "banner",key = "'teacherCourseList'")
    public R getIndex(){

        QueryWrapper<EduTeacher> wrapperTeacher = new QueryWrapper<>();
        // 降序排序
        wrapperTeacher.orderByDesc("id");
        // sql语句后面添加的语句
        wrapperTeacher.last("limit 4");
        List<EduTeacher> teacherList = eduTeacherService.list(wrapperTeacher);
        System.out.println("==============================讲师数量：" + teacherList.size());

        QueryWrapper<EduCourse> wrapperCourse = new QueryWrapper();
        wrapperCourse.orderByDesc("id");
        wrapperCourse.last("limit 8");
        List<EduCourse> courseList = eduCourseService.list(wrapperCourse);
        System.out.println("===============================课程数量：" + courseList.size());
        return R.ok().data("teacherList",teacherList).data("courseList",courseList);

    }


}
