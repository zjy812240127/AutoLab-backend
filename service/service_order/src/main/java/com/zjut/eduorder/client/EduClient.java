package com.zjut.eduorder.client;

import com.zjut.commonutils.EduCourseOrder;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Component
@FeignClient("service-edu")
public interface EduClient {

    @ApiOperation(value = "创建订单时根据课程id获取课程信息，返回公用的课程类对象")
    @GetMapping("/eduservice/course/getCourseOrder/{courseId}")
    public EduCourseOrder getCourseById(
            @ApiParam(name = "courseId", value = "远程调用时传过来的课程id",required = true)
            @PathVariable String courseId);

    }
