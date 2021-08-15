package com.zjut.fileservice.mapper;

import com.zjut.fileservice.entity.EduFile;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.Date;
import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author testjava
 * @since 2021-08-02
 */
public interface EduFileMapper extends BaseMapper<EduFile> {

    List<EduFile> findFileList(long current, long limit);

    List<EduFile> getFileList(long current, long limit);

    EduFile getFileById(String fileId);

    List<EduFile> findFileByInfo(long current, long limit, String title, Date begin, Date end);
}
