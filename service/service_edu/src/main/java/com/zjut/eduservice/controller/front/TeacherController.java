package com.zjut.eduservice.controller.front;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sun.xml.bind.v2.model.core.ID;
import com.zjut.commonutils.R;
import com.zjut.eduservice.entity.EduChapter;
import com.zjut.eduservice.entity.EduCourse;
import com.zjut.eduservice.entity.EduTeacher;
import com.zjut.eduservice.service.EduCourseService;
import com.zjut.eduservice.service.EduTeacherService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

//@CrossOrigin
@RestController
@RequestMapping("/eduservice/teacherFront")
public class TeacherController {

    @Autowired
    private EduTeacherService eduTeacherService;

    @Autowired
    private EduCourseService eduCourseService;

    @ApiOperation(value = "nuxt分页讲师列表")
    @GetMapping("/{page}/{limit}")
    public R getTeacherPageList(
            @ApiParam(name = "page", value = "当前页", required = true)
            @PathVariable Long page,
            @ApiParam(name = "limit", value = "每页记录数量", required = true)
            @PathVariable Long limit){

        System.out.println("===================获取前端讲师列表=====================");

        Page<EduTeacher> pageTeacher = new Page<>(page, limit);
        // 由于前端不用element-ui所以后端需要传递所有分页信息，不只是记录总数和所有数据行
        Map<String, Object> map = eduTeacherService.pageListWeb(pageTeacher);
        return R.ok().data(map);
    }

    @ApiOperation(value = "根据讲师id查询讲师所讲课程的列表")
    @GetMapping("/getTeacherListById/{id}")
    public R GetListById(@PathVariable String id){
        EduTeacher eduTeacher = eduTeacherService.getById(id);
        List<EduCourse> courseList = eduCourseService.getListByTeacherId(id);

        return R.ok().data("teacher",eduTeacher).data("courseList",courseList);
    }


}

