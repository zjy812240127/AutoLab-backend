package com.zjut.eduservice.service;

import com.zjut.eduservice.entity.EduSubject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zjut.eduservice.entity.subject.oneSubject;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author testjava
 * @since 2021-07-01
 */
public interface EduSubjectService extends IService<EduSubject> {

    void saveSubject(MultipartFile file, EduSubjectService eduSubjectService);

    List<oneSubject> getOneTwoSubject();
}
