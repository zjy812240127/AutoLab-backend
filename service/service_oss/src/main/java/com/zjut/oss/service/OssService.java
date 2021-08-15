package com.zjut.oss.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author Joya Zou
 * @date 2021年07月01日 9:15
 */
public interface OssService {
    String upLoaderFileAvatar(MultipartFile file);

    String upLoaderSources(MultipartFile file);

    String uploadBanners(MultipartFile file);
}
