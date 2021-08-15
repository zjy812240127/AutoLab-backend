package com.zjut.eduservice.controller;


import com.zjut.commonutils.R;
import com.zjut.eduservice.client.VodClient;
import com.zjut.eduservice.entity.EduVideo;
import com.zjut.eduservice.service.EduVideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-07-02
 */
@RestController
@RequestMapping("/eduservice/video")
//@CrossOrigin  // 使用spring cloud gateway时此处不能加注解，会造成二次跨域
public class EduVideoController {

    @Autowired
    private EduVideoService eduVideoService;

    @Autowired
    private VodClient vodClient;

    @GetMapping(value = "/findVideo/{id}")
    public R findVideo(@PathVariable String id){

        final EduVideo video = eduVideoService.getById(id);

        return R.ok().data("video",video);
    }


    // 添加小节
    @PostMapping(value = "/addVideo")
    public R addVideo(@RequestBody EduVideo eduVideo){
        final boolean save = eduVideoService.save(eduVideo);
        return R.ok();
    }

    // 删除小节
    // TODO 删除小节时要把云端的视频也删除
    @DeleteMapping(value = "/deleteVideo/{id}")
    public R removeVideo(@PathVariable String id){

        // 根据小节id获取视频id
        EduVideo video = eduVideoService.getById(id);
        String videoSourceId = video.getVideoSourceId();
        // 判断是否有视频
        if(! StringUtils.isEmpty(videoSourceId)){
            // 调用远程服务删除视频
            vodClient.removeById(videoSourceId);
        }

        eduVideoService.removeById(id);
        return R.ok();
    }

    // 修改小节
    @PostMapping(value = "/updateVideo")
    public R updateVideo(@RequestBody EduVideo eduVideo){
        eduVideoService.updateById(eduVideo);
        return R.ok();
    }
}

