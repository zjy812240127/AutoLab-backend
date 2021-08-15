package com.zjut.oss.controller;

import com.zjut.commonutils.R;
import com.zjut.oss.OssApplication;
import com.zjut.oss.service.OssService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Joya Zou
 * @date 2021年07月01日 9:14
 */
@RestController
@RequestMapping(value = "/eduoss/fileoss")
//@CrossOrigin  // 使用spring cloud gateway时此处不能加注解，会造成二次跨域
public class OssController {

    @Autowired
    private OssService ossService;

    // 上传头像的方法
    @PostMapping
    public R uploadOssFile(MultipartFile file){
        // 获取上传文件,返回上传的文件存储的url路径
        String url = ossService.upLoaderFileAvatar(file);

        return R.ok().data("url",url);
    }

    // 上传文件资源的方法
    @PostMapping("/uploadFiles")
    public R uploadSources(MultipartFile file){
        // 获取上传文件,返回上传的文件存储的url路径
        String url = ossService.upLoaderSources(file);
        // 返回可以下载文件的路径
        return R.ok().data("url",url);
    }

    @ApiOperation("上传banner到阿里云oss")
    @PostMapping("/uploadBanners")
    public String uploadBanner(MultipartFile file){

        String url = ossService.uploadBanners(file);
        return url;
    }




}
