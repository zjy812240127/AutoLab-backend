package com.zjut.eduservice.controller.front;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zjut.commonutils.R;
import com.zjut.eduservice.entity.EduCourse;
import com.zjut.eduservice.entity.vo.CourseQueryVo;
import com.zjut.eduservice.service.EduCourseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@Api("分页条件查询课程列表所需的信息")
//@CrossOrigin
@RequestMapping("/eduservice/courseFront")
public class CourseList {

    @Autowired
    private EduCourseService eduCourseService;

    // 前端没有使用element-UI所以这里查询的数据不只是total和rows
    // 使用了@RequestBody则必须要用PostMapping
    @ApiOperation(value = "分页条件查询课程信息，因为前端分页的情况很多，统一由courseQueryVo来接收条件参数")
    @PostMapping("/{page}/{limit}")
    public R getCourseList(
            @ApiParam(name = "page", value = "当前页", required = true)
            @PathVariable Long page,
            @ApiParam(name = "limit", value = "每页记录条数", required = true)
            @PathVariable Long limit,
            @ApiParam(name = "courseQueryVo" , value = "前端传入的要查询的对象", required = true)
            @RequestBody(required = false) CourseQueryVo courseQueryVo
            ){

        System.out.println("===============查询课程列表信息==================");
        // courseQueryVo用来统一接受前端传过来的参数
        Page<EduCourse> coursePage = new Page<>(page,limit);
        Map<String,Object> map = eduCourseService.getCourseList(coursePage,courseQueryVo);
        return R.ok().data(map);

    }


}
