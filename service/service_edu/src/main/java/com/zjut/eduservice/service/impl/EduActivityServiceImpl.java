package com.zjut.eduservice.service.impl;

import com.zjut.eduservice.entity.EduActivity;
import com.zjut.eduservice.mapper.EduActivityMapper;
import com.zjut.eduservice.service.EduActivityService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zjut.serviceBase.exceptionHandler.MyException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author testjava
 * @since 2021-08-10
 */
@Service
public class EduActivityServiceImpl extends ServiceImpl<EduActivityMapper, EduActivity> implements EduActivityService {

    @Override
    public void deleteById(String activityId) {
        // 删除oss上的图片数据
        System.out.println("请到oss删除活动的图片");
        // 删除阿里云视频点播上的数据
        System.out.println("请到阿里云视频点播删除活动相关ship");
        // 删除activity表中的数据
        int delete = baseMapper.deleteById(activityId);
        if (delete == 0){
            throw new MyException(20001,"activity表删除活动信息失败");
        }
    }

    @Override
    public List<EduActivity> getHot() {

        List<EduActivity> eduActivityList = baseMapper.getHotActivity();
        return eduActivityList;
    }
}
