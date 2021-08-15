package com.zjut.vod.controller;


import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.zjut.commonutils.R;
import com.zjut.vod.Utils.ConstantVodUtil;
import com.zjut.vod.Utils.InitVodClient;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

//@CrossOrigin  // 使用spring cloud gateway时此处不能加注解，会造成二次跨域
@RequestMapping("/eduvod/video")
@Api("阿里云视频点播微服务")
@RestController
public class VideoController {

    @GetMapping("getPlayAuth/{videoId}")
    public R getPlayAuth(@PathVariable String videoId) throws ClientException {
        System.out.println("尝试获取视频播放凭证+++++++++++++++++++++++++");
        //获取阿里云存储相关常量
        String accessKeyId = ConstantVodUtil.ACCESS_KEY_ID;
        String accessKeySecret = ConstantVodUtil.ACCESS_KEY_SECRET;
        //初始化
        DefaultAcsClient client = InitVodClient.initVodClient(accessKeyId,
                accessKeySecret);
        //请求
        GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
        request.setVideoId(videoId);
        //响应
        GetVideoPlayAuthResponse response = client.getAcsResponse(request);
        //得到播放凭证
        String playAuth = response.getPlayAuth();

        System.out.println("视频的凭证=" + playAuth);

        return R.ok().data("playAuth",playAuth).message("获取凭证成功");

    }


}
