package com.zjut.eduservice.service;

import com.zjut.eduservice.entity.EduVideo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程视频 服务类
 * </p>
 *
 * @author testjava
 * @since 2021-07-02
 */
public interface EduVideoService extends IService<EduVideo> {

    void removeByCourseId(String id);

}
