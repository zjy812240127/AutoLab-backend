package com.zjut.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zjut.eduservice.entity.EduChapter;
import com.zjut.eduservice.entity.EduVideo;
import com.zjut.eduservice.entity.chapter.ChapterVo;
import com.zjut.eduservice.entity.chapter.VideoVo;
import com.zjut.eduservice.mapper.EduChapterMapper;
import com.zjut.eduservice.service.EduChapterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zjut.eduservice.service.EduVideoService;
import com.zjut.serviceBase.exceptionHandler.MyException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2021-07-02
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {

    @Autowired
    private EduVideoService eduVideoService;


    @Override
    public List<ChapterVo> findChapter() {

        // 1.查询所有的chpter
        // 2.遍历每一个chapter，将属性赋值给ChapterVo对象
        // 3.查询每一个chapter对应的video，将属性赋值给VideoVo对象
        // 4.将Video对象加入ChpaterVo的children列表中
        // 5.将每一个ChapterVo对象加入到该方法的返回list中
        List<ChapterVo> result = new ArrayList<>();
        // 查询所有chapter
        final List<EduChapter> eduChaptersList = baseMapper.selectList(null);
        // 查询所有video
        final List<EduVideo> videosList = eduVideoService.list(null);

        for(int i=0; i<eduChaptersList.size(); i++){
            // 传到前端的chapter
            ChapterVo chapterVo = new ChapterVo();
            BeanUtils.copyProperties(eduChaptersList.get(i),chapterVo);

            String cId = chapterVo.getId();
            List<VideoVo> childrenList = new ArrayList<>();
            for(int j=0; j<videosList.size(); j++){
                if(cId.equals(videosList.get(j).getChapterId())){
                    // 传到前端的video
                    VideoVo videoVo = new VideoVo();
                    BeanUtils.copyProperties(videosList.get(j),videoVo);
                    childrenList.add(videoVo);
                }
            }
            chapterVo.setChildren(childrenList);

            result.add(chapterVo);
        }

        return result;
    }

    @Override
    public List<ChapterVo> findChapterById(String id) {

        List<ChapterVo> result = new ArrayList<>();
        // 查询对应id的chapter
        final EduChapter eduChapter = baseMapper.selectById(id);
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("course_id",id);
        final List chapterList = baseMapper.selectList(wrapper);

        // 查询所有video
        final List<EduVideo> videosList = eduVideoService.list(null);


        for(int i =0; i<chapterList.size(); i++){
            ChapterVo chapterVo = new ChapterVo();
            BeanUtils.copyProperties(chapterList.get(i),chapterVo);

            String cId = chapterVo.getId();

            List<VideoVo> childrenList = new ArrayList<>();
            for(int j=0; j<videosList.size(); j++){
                if(cId.equals(videosList.get(j).getChapterId())){
                    // 传到前端的video
                    VideoVo videoVo = new VideoVo();
                    BeanUtils.copyProperties(videosList.get(j),videoVo);
                    childrenList.add(videoVo);
                }
            }
            chapterVo.setChildren(childrenList);
            result.add(chapterVo);
        }
        return result;

    }

    @Override
    public void addChapter(EduChapter eduChapter) {
        final int insert = baseMapper.insert(eduChapter);
        if (insert <= 0){
            throw new MyException(20001,"插入章节失败");
        }
    }

    @Override
    public void deleteChapter(String id) {

        QueryWrapper wrapper = new QueryWrapper();
        // 查询章节下是否有小节
        wrapper.eq("chapter_id", id);
        System.out.println("chapter_id"+id);

        final int count = eduVideoService.count(wrapper);
        // 如果没有小节就删除
        if (count > 0){
            throw new MyException(20001,"有小节不能删除");
        }else {
            final int delete = baseMapper.deleteById(id);
        }

    }

    @Override
    public void remodeByCourseId(String id) {
        QueryWrapper<EduChapter> wrapperChapter = new QueryWrapper<>();
        wrapperChapter.eq("course_id",id);
        baseMapper.delete(wrapperChapter);

    }
}
