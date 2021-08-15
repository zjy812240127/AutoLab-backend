package com.zjut.fileservice.service;

import com.zjut.fileservice.entity.EduFile;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zjut.fileservice.entity.vo.FileQuery;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author testjava
 * @since 2021-08-02
 */
public interface EduFileService extends IService<EduFile> {

    String uploadFile(MultipartFile file);

    void saveDB(EduFile eduFile);

    List<EduFile> getFileList( long current, long limit, String title, String begin, String end);

    void updateFile(FileQuery fileQuery);

    Map<String, Object> getList(long current, long limit);

    EduFile getFileById(String fileId);

    Map<String, Object> getFileByInfo(long current, long limit, FileQuery fileQuery);

}
