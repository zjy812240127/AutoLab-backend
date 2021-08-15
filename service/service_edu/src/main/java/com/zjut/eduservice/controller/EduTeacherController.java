package com.zjut.eduservice.controller;



import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zjut.commonutils.R;
import com.zjut.eduservice.entity.EduTeacher;
import com.zjut.eduservice.entity.vo.TeacherQuery;
import com.zjut.eduservice.service.EduTeacherService;
import com.zjut.serviceBase.exceptionHandler.MyException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.sql.Wrapper;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-06-27
 */
@Api(description="讲师管理")  // 在swagger中显示注释
@RestController
@RequestMapping("/eduservice/teacher")
//@CrossOrigin  // 使用spring cloud gateway时此处不能加注解，会造成二次跨域
public class EduTeacherController {

    @Autowired
    private EduTeacherService eduTeacherService;


    @ApiOperation("查询资历最老的讲师四名")
    @GetMapping("/getHotTeachers")
    public R getOldTeachers(){
        long startTime = System.currentTimeMillis();
        List<EduTeacher> teacherList = eduTeacherService.getOlds();
        long endTime = System.currentTimeMillis();
        long time = endTime - startTime;
        System.out.println("查询所用的时间为：" + time);
        return R.ok().data("list",teacherList);
    }

    // 查询所有记录
    // RESTfull风格
    @ApiOperation(value = "所有讲师列表")  // 在swagger中显示注释
    @GetMapping(value = "/findAll")
    public R findAll(){
        List<EduTeacher> list = eduTeacherService.list(null);
        // ok表示返回的是操作成功，操作的结果数据由方法data存储
        // 链式编程

        // 测试全局异常处理
        // try{
        //     int i = 10/0;
        // }catch (Exception e){
        //     throw new MyException(20001,"执行了自定义异常....");
        // }

        return R.ok().data("items",list);
    }

    // 逻辑删除
    // RESTfull风格从路径中获取参数
    @ApiOperation(value = "根据ID删除讲师")
    @DeleteMapping("{id}")
    public R removeTeacher( @ApiParam(name = "id", value = "讲师ID", required = true)
                    @PathVariable String id){
        boolean flag = eduTeacherService.removeById(id);

        if(flag){
            return R.ok();
        }else {
            return R.error();
        }

    }

    // 分页查询
    @GetMapping(value = "/pageTeacher/{current}/{limit}")
    public R pageTeacher(@ApiParam(name = "page", value = "当前页码", required = true)
                        @PathVariable Long current,
                         @ApiParam(name = "limit", value = "每页记录数", required = true)
                         @PathVariable Long limit){
        // 创建page对象
        Page<EduTeacher> page = new Page<>(current,limit);
        // 调用mp的接口方法实现分页，底层将分页结果写入page中
        eduTeacherService.page(page,null);

        long total = page.getTotal();  // 总记录数
        List<EduTeacher> records = page.getRecords();

        Map map = new HashMap();
        map.put("total",total);
        map.put("rows",records);
        return R.ok().data(map);

    }


    // 条件分页查询
    // 使用@RequestBody表示以json的格式将前端的参数封装到一个类中
    // 使用@RequestBody以后必须以post的形式请求
    // required = false 表示可以为空
    @PostMapping(value = "/pageTeacherCondition/{current}/{limit}")
    public R pageTeacherCondition(@PathVariable long current,
                                  @PathVariable long limit,
                                  @RequestBody(required = false) TeacherQuery teacherQuery
                                  ){
        // 创建page对象
        Page<EduTeacher> page = new Page<>(current,limit);
        // 构建条件
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();
        // 多条件组合查询
        // 动态sql语句，判断参数是否为空
        final String name = teacherQuery.getName();
        final Integer level = teacherQuery.getLevel();
        final String begin = teacherQuery.getBegin();
        final String end = teacherQuery.getEnd();

        if (!StringUtils.isEmpty(name)){
            // 构建条件
            wrapper.like("name",name);
        }

        if (!StringUtils.isEmpty(level)){
            // 构建条件
            wrapper.eq("level",level);
        }

        if (!StringUtils.isEmpty(begin)){
            // 大于起始时间点
            wrapper.ge("gmt_create",begin);
        }

        if (!StringUtils.isEmpty(end)){
            // 小于结束时间点
            wrapper.le("gmt_create",end);
        }

        // 将最近改动的数据显示在最前面
        wrapper.orderByDesc("gmt_create");

        // 调用方法实现条件查询分页
        eduTeacherService.page(page,wrapper);
        final long total = page.getTotal();  // 总记录条数
        final List<EduTeacher> records = page.getRecords();  // 查询结果数据集
        Map map = new HashMap();
        map.put("total",total);
        map.put("rows",records);
        return R.ok().data(map);
    }

    // 添加讲师
    @PostMapping(value = "/addTeacher")
    public R addTeacher(@RequestBody EduTeacher eduTeacher){
        final boolean flag = eduTeacherService.save(eduTeacher);

        if(flag){
            return R.ok();
        }else {
            return R.error();
        }
    }

    // 根据id查询
    @GetMapping(value = "/getById/{id}")
    public R queryById(@PathVariable String id){
        final EduTeacher eduTeacher = eduTeacherService.getById(id);
        return R.ok().data("eduTeacher",eduTeacher);
    }

    // 修改
    @PostMapping(value = "/updateTeacher")
    public R updateTeacher(@RequestBody EduTeacher eduTeacher){
        final boolean flag = eduTeacherService.updateById(eduTeacher);
        if(flag){
            return R.ok();
        }else {
            return R.error();
        }
    }





}

