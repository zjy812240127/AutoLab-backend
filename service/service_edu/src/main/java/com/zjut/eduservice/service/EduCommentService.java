package com.zjut.eduservice.service;

import com.zjut.eduservice.entity.EduComment;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 评论 服务类
 * </p>
 *
 * @author testjava
 * @since 2021-08-06
 */
public interface EduCommentService extends IService<EduComment> {

    void addComment(EduComment eduComment);

}
