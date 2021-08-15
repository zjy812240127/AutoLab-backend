package com.zjut.eduservice.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zjut.eduservice.entity.EduSubject;
import com.zjut.eduservice.entity.excel.SubjectData;
import com.zjut.eduservice.service.EduSubjectService;
import com.zjut.serviceBase.exceptionHandler.MyException;
import org.springframework.stereotype.Service;

/**
 * @author Joya Zou
 * @date 2021年07月01日 20:28
 */

public class SubjectExcelListener extends AnalysisEventListener<SubjectData> {

    // 因为SubjectExcelListener要在EduSubjectServiceImpl中被EasyExcel.read()方法手动new对象，
    // 所以不能加注解将其交给spring管理
    // 需要用构造方法传递对象

    // SubjectExcelListener的有参构造函数需要传入参数EduSubjectService
    public EduSubjectService eduSubjectService;
    public SubjectExcelListener(){};
    public SubjectExcelListener(EduSubjectService eduSubjectService) {
        this.eduSubjectService = eduSubjectService;
    }

    // 按行读取数据
    @Override
    public void invoke(SubjectData subjectData, AnalysisContext analysisContext) {
        // 如果文件没有数据
        if(subjectData == null){
            throw new MyException(20001,"文件数据为空");
        }
        // 按行读取，每次有两个数据，第一个是一级分类，第二个是二级分类
        EduSubject existOneSubject = this.existOneSubject(eduSubjectService, subjectData.getOneSubjectName());

        // 添加一级分类数据
        // 为空表示数据库中没有数据，执行添加操作
        if(existOneSubject == null){
            existOneSubject = new EduSubject();
            existOneSubject.setParentId("0");
            existOneSubject.setTitle(subjectData.getOneSubjectName());
            eduSubjectService.save(existOneSubject);
        }

        // 添加二级分类数据
        // 获取一级分类的id字段值作为二级分类的pid
        String pid = existOneSubject.getId();

        EduSubject existTwoSubject = this.existTwoSubject(eduSubjectService, subjectData.getOneSubjectName(), pid);
        // 为空表示数据库中不存在记录，执行添加操作
        if(existTwoSubject == null){
            existTwoSubject = new EduSubject();
            existTwoSubject.setParentId(pid);
            existTwoSubject.setTitle(subjectData.getTwoSubjectName());
            eduSubjectService.save(existTwoSubject);
        }

    }

    // 如果数据库中已有的记录不能重复添加
    // 判断一级分类是否存在于数据库中
    private EduSubject existOneSubject(EduSubjectService eduSubjectService, String name){
        // 按条件查询
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        // 为一级分类的记录
        wrapper.eq("parent_id",0);
        // 不能是以及存在于数据库中的记录
        wrapper.eq("title",name);
        // 按条件查询
        EduSubject oneSubject = eduSubjectService.getOne(wrapper);
        // 如果返回有记录表明重复添加了
        return oneSubject;
    }

    // 判断二级分类是否存在于数据库中
    private EduSubject existTwoSubject(EduSubjectService eduSubjectService, String name, String pid){
        // 按条件查询
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        // 为一级分类的记录
        wrapper.eq("parent_id",pid);
        // 不能是以及存在于数据库中的记录
        wrapper.eq("title",name);
        // 按条件查询
        EduSubject twoSubject = eduSubjectService.getOne(wrapper);
        // 如果返回有记录表明重复添加了
        return twoSubject;
    }





    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
