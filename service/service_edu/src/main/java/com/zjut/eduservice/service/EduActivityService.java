package com.zjut.eduservice.service;

import com.zjut.eduservice.entity.EduActivity;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author testjava
 * @since 2021-08-10
 */
public interface EduActivityService extends IService<EduActivity> {

    void deleteById(String activityId);

    List<EduActivity> getHot();

}
