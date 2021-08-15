package com.zjut.eduservice.service.impl;

import com.zjut.eduservice.entity.EduComment;
import com.zjut.eduservice.mapper.EduCommentMapper;
import com.zjut.eduservice.service.EduCommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zjut.serviceBase.exceptionHandler.MyException;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 评论 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2021-08-06
 */
@Service
public class EduCommentServiceImpl extends ServiceImpl<EduCommentMapper, EduComment> implements EduCommentService {

    @Override
    public void addComment(EduComment eduComment) {

        // 录入comment表
        int insertComment = baseMapper.insert(eduComment);
        if (insertComment ==0){
            throw new MyException(20001,"录入comment表失败");
        }

    }
}
