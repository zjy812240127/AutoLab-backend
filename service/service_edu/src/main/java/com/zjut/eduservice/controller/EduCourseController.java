package com.zjut.eduservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zjut.commonutils.EduCourseOrder;
import com.zjut.commonutils.JwtUtils;
import com.zjut.commonutils.R;
import com.zjut.eduservice.client.OrderClient;
import com.zjut.eduservice.entity.EduChapter;
import com.zjut.eduservice.entity.EduCourse;
import com.zjut.eduservice.entity.EduTeacher;
import com.zjut.eduservice.entity.chapter.ChapterVo;
import com.zjut.eduservice.entity.vo.AddCourseVo;
import com.zjut.eduservice.entity.vo.CoursePublishVo;
import com.zjut.eduservice.entity.vo.CourseWebVo;
import com.zjut.eduservice.entity.vo.TeacherQuery;
import com.zjut.eduservice.service.EduChapterService;
import com.zjut.eduservice.service.EduCourseService;
import com.zjut.serviceBase.exceptionHandler.MyException;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-07-02
 */
@RestController
@RequestMapping("/eduservice/course")
//@CrossOrigin  // 使用spring cloud gateway时此处不能加注解，会造成二次跨域
public class EduCourseController {

    @Autowired
    private EduCourseService eduCourseService;
    @Autowired
    private EduChapterService eduChapterService;
    @Autowired
    private OrderClient orderClient;


    // 首页展示时按照课程的访问量展示前八门热点课程
    @ApiOperation("热点课程展示")
    @GetMapping("/getHotCourses")
    public R getHotCourses(){
        long startTime = System.currentTimeMillis();
        List<EduCourse> courseList = eduCourseService.getHot();
        long endTime = System.currentTimeMillis();
        long time = endTime - startTime;
        System.out.println("查询所用时间：" + time);

        return R.ok().data("list",courseList);
    }

    // 查询所有课程信息
    @GetMapping(value = "/findAllCourse")
    public R findAllCourse(){
        final List<EduCourse> courseList = eduCourseService.list(null);
        final int count = eduCourseService.count(null);
        return R.ok().data("courseList",courseList).data("total",count);
    }

    // 条件分页查询
    // 使用@RequestBody表示以json的格式将前端的参数封装到一个类中
    // 使用@RequestBody以后必须以post的形式请求
    // required = false 表示可以为空
    @PostMapping(value = "/pageCourseCondition/{current}/{limit}")
    public R pageCourseCondition(@PathVariable long current,
                                  @PathVariable long limit,
                                  @RequestBody(required = false) EduCourse courseQuery
    ){
        // 创建page对象
        Page<EduCourse> page = new Page<>(current,limit);
        // 构建条件
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        // 多条件组合查询
        // 动态sql语句，判断参数是否为空
        String title = courseQuery.getTitle();
        String status = courseQuery.getStatus();
        System.out.println("title" + title + "statue" + status);

        if (!StringUtils.isEmpty(title)){
            // 构建条件
            wrapper.like("title",title);
        }

        if (!StringUtils.isEmpty(status)){
            // 构建条件
            wrapper.eq("status",status);
        }

        // 将最近改动的数据显示在最前面
        wrapper.orderByDesc("gmt_create");

        // 调用方法实现条件查询分页
        eduCourseService.page(page,wrapper);
        List<EduCourse> records = page.getRecords();
        long total = page.getTotal();
        //long total = page.getTotal();  // 总记录条数
        //List<EduCourse> records = page.getRecords();  // 查询结果数据集
         //Map map = new HashMap();
         //map.put("total",total);
         //map.put("rows",records);
        System.out.println("执行条件查询课程================" + "total" + total);
        return R.ok().data("total",total).data("rows",records);
    }





    @PostMapping(value = "/addCourse")
    public R addCourse(@RequestBody AddCourseVo addCourseVo){

        // 返回添加之后的课程的id，前端拿到该id后添加相应的课程大纲
        String id = eduCourseService.addCourse(addCourseVo);

        return R.ok().data("id",id);
    }

    // 根据课程id查询课程信息
    @GetMapping(value = "/findById/{id}")
    public R findById(@PathVariable String id){

        AddCourseVo courseVo = eduCourseService.findById(id);
        return R.ok().data("course",courseVo);
    }

    // 根据课程id删除课程
    @PostMapping(value = "/deleteById/{id}")
    public R deleteById(@PathVariable String id){
        eduCourseService.deleteCourse(id);
        return R.ok();
    }

    // 修改课程信息
    @PostMapping(value = "/updateCourse")
    public R updateCourse(@RequestBody AddCourseVo courseVo){

        eduCourseService.updateCourse(courseVo);

        return R.ok();

    }

    // 根据课程id查询课程发布信息
    @GetMapping(value = "/getCoursePublishInfo/{id}")
    public R getPublishInfo(@PathVariable String id){
        CoursePublishVo publishVo = eduCourseService.getPublishInfo(id);
        return R.ok().data("publishVo",publishVo);
    }

    @PostMapping(value = "/publish/{id}")
    public R publishCourse(@PathVariable String id){
        final EduCourse eduCourse = new EduCourse();
        eduCourse.setId(id);
        eduCourse.setStatus("Normal");
        final boolean update = eduCourseService.updateById(eduCourse);

        return R.ok();
    }

    @ApiOperation(value = "根据id查询课程信息")
    @GetMapping("{courseId}")
    public R getById(
            @ApiParam(name = "courseId", value = "课程id", required = true)
            @PathVariable String courseId,
            HttpServletRequest request){
        // 查询课程和讲师信息
        CourseWebVo courseWebVo = eduCourseService.getCourseInfo(courseId);
        // 查询当前课程的章节信息
        List<ChapterVo> chapterVoList = eduChapterService.findChapterById(courseId);
        //EduChapter chapter = eduChapterService.getById(courseId);
        // 查询当前课程是否已经被当前用户购买
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        System.out.println("查询是否已经购买课程时用户id为：" +memberId );
        boolean flag = orderClient.confirmOrder(memberId, courseId);
        System.out.println("当前用户是否已经购买该课程？" + flag);
        return R.ok().data("course",courseWebVo).data("chapter",chapterVoList).data("flag",flag);
    }


    @ApiOperation(value = "创建订单时根据课程id获取课程信息，返回公用的课程类对象")
    @GetMapping("/getCourseOrder/{courseId}")
    public EduCourseOrder getCourseById(
            @ApiParam(name = "courseId", value = "远程调用时传过来的课程id",required = true)
            @PathVariable String courseId){

        EduCourse eduCourse = eduCourseService.getById(courseId);
        EduCourseOrder eduCourseOrder = new EduCourseOrder();
        BeanUtils.copyProperties(eduCourse,eduCourseOrder);
        return eduCourseOrder;

    }




    public int testMyTest(){
        return 1;
    }



}

