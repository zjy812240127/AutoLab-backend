package com.zjut.vod.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author Joya Zou
 * @date 2021年07月08日 8:51
 */
public interface VodService {
    String upload(MultipartFile file);

    void removeById(String videoId);

}
