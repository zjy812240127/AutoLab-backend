package com.zjut.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zjut.eduservice.entity.EduVideo;
import com.zjut.eduservice.mapper.EduVideoMapper;
import com.zjut.eduservice.service.EduVideoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2021-07-02
 */
@Service
public class EduVideoServiceImpl extends ServiceImpl<EduVideoMapper, EduVideo> implements EduVideoService {

    // TODO 删除小节的同时删除对应的视频

    @Override
    public void removeByCourseId(String id) {
        QueryWrapper<EduVideo> wrapperVideo = new QueryWrapper<>();
        wrapperVideo.eq("course_id",id);
        baseMapper.delete(wrapperVideo);
    }
}
