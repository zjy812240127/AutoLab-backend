package com.zjut.eduservice.controller;


import com.zjut.commonutils.R;
import com.zjut.eduservice.entity.EduChapter;
import com.zjut.eduservice.entity.chapter.ChapterVo;
import com.zjut.eduservice.service.EduChapterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-07-02
 */
@RestController
@RequestMapping("/eduservice/chapter")
//@CrossOrigin  // 使用spring cloud gateway时此处不能加注解，会造成二次跨域
public class EduChapterController {

    @Autowired
    private EduChapterService eduChapterService;

    // 查询所有章节以及小节，一个章节对应多个小节
    @GetMapping("/findChapter")
    public R findChapter(){

        List<ChapterVo> list = eduChapterService.findChapter();

        return R.ok().data("list",list);
    }


    // 根据课程id查询课程大纲
    @GetMapping("/findChapterById/{id}")
    public R findChapterById(@PathVariable String id){

        List<ChapterVo> list = eduChapterService.findChapterById(id);

        return R.ok().data("list",list);
    }

    // 添加章节
    @PostMapping(value = "/addChapter")
    public R addChapter(@RequestBody EduChapter eduChapter){

        eduChapterService.save(eduChapter);
        return R.ok();
    }

    // 根据id查询chapter信息，返回chapter 不返回chpatervo
    @GetMapping(value = "/findById/{chapterId}")
    public R findById(@PathVariable String chapterId){
        final EduChapter eduChapter = eduChapterService.getById(chapterId);
        System.out.println("++++++++++++++++++++");
        System.out.println(eduChapter.getTitle());
        return R.ok().data("chapter",eduChapter);
    }

    // 修改章节
    @PostMapping(value = "/updateChapter")
    public R updateChpater(@RequestBody EduChapter eduChapter){
        final boolean update = eduChapterService.updateById(eduChapter);

        return R.ok();
    }

    // 删除chapter
    @DeleteMapping(value = "/deleteChapter/{id}")
    public R deleteChapter(@PathVariable String id){
        // 自定义删除方法，如果章节下有小节，先不允许删除
        eduChapterService.deleteChapter(id);

      

        return R.ok();
    }


}

