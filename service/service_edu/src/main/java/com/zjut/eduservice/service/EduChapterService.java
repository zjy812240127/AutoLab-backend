package com.zjut.eduservice.service;

import com.zjut.eduservice.entity.EduChapter;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zjut.eduservice.entity.chapter.ChapterVo;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author testjava
 * @since 2021-07-02
 */
public interface EduChapterService extends IService<EduChapter> {

    List<ChapterVo> findChapter();

    List<ChapterVo> findChapterById(String id);

    void addChapter(EduChapter eduChapter);

    void deleteChapter(String id);

    void remodeByCourseId(String id);

}
