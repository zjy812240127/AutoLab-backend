package com.zjut.vod.controller;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.zjut.commonutils.R;
import com.zjut.serviceBase.exceptionHandler.MyException;
import com.zjut.vod.Utils.ConstantVodUtil;
import com.zjut.vod.Utils.InitVodClient;
import com.zjut.vod.service.VodService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.*;

import javax.annotation.PostConstruct;

/**
 * @author Joya Zou
 * @date 2021年07月08日 8:48
 */
@RestController
@RequestMapping(value = "/eduvod/video")
//@CrossOrigin  // 使用spring cloud gateway时此处不能加注解，会造成二次跨域
public class VodController {

    @Autowired
    private VodService vodService;

    // 上传视频到阿里云服务器
    @PostMapping(value = "/uploadVideo")
    public R uploadVideo(@RequestParam("file") MultipartFile file) throws Exception{

        String vodId = vodService.upload(file);

        return R.ok().data("vodId",vodId);
    }

    // 删除视频
    @DeleteMapping(value = "/removeById/{id}")
    public R removeById(@PathVariable String id){
        try{
            // 初始化对象
            final DefaultAcsClient client = InitVodClient.initVodClient(ConstantVodUtil.ACCESS_KEY_ID, ConstantVodUtil.ACCESS_KEY_SECRET);
            // 创建删除视频的request对象
            DeleteVideoRequest request = new DeleteVideoRequest();
            // 向request设置视频id
            request.setVideoIds(id);
            // 调用方法实现删除视频
            client.getAcsResponse(request);
            return R.ok();

        }catch (Exception e){
            e.printStackTrace();
            throw new MyException(20001,"删除视频失败");

        }
    }

    // 根据id删除视频
    @DeleteMapping("{videoId}")
    public R removeVodById(@PathVariable String videoId){
        vodService.removeById(videoId);
        return R.ok().message("视频删除成功");
    }






}

