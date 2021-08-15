package com.zjut.eduservice.client;

import com.zjut.commonutils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author Joya Zou
 * @date 2021年07月11日 11:57
 */

@FeignClient("service-vod")  // 调用远程服务的名称，nacos中的注册名
@Component
public interface VodClient {

    // 定义要调用的远程服务中的方法路径
    // 可以直接去复制，使用完整的路径
    // 删除视频
    @DeleteMapping(value = "/eduvod/video/removeById/{id}")
    public R removeById(@PathVariable String id);

}