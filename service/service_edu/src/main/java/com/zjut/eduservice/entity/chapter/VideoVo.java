package com.zjut.eduservice.entity.chapter;

import lombok.Data;

/**
 * @author Joya Zou
 * @date 2021年07月03日 16:05
 */
@Data
public class VideoVo {

    private String id;

    private String title;

    private String videoSourceId;  // 视频在阿里云的id，后期用该id获取播放凭证


}
