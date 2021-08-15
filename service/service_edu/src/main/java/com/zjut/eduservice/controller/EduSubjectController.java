package com.zjut.eduservice.controller;


import com.zjut.commonutils.R;
import com.zjut.eduservice.entity.EduSubject;
import com.zjut.eduservice.entity.subject.oneSubject;
import com.zjut.eduservice.service.EduSubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-07-01
 */
@RestController
@RequestMapping("/eduservice/subject")
//@CrossOrigin  // 使用spring cloud gateway时此处不能加注解，会造成二次跨域
public class EduSubjectController {

    @Autowired
    private EduSubjectService eduSubjectService;

    // 添加课程分类
    // 获取前端上传的excel文件，把文件内容读取出来
    @PostMapping(value = "/addSubject")
    public R addSubject(MultipartFile file){
        // 添加课程分类
        eduSubjectService.saveSubject(file,eduSubjectService);
        return R.ok();
    }

    // 返回课程分类列表
    @GetMapping(value = "/listSubject")
    public R list(){

        List<oneSubject> oneList =  eduSubjectService.getOneTwoSubject();

        return R.ok().data("list",oneList);
    }



}

