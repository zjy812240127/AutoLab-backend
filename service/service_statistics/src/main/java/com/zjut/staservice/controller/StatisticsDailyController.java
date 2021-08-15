package com.zjut.staservice.controller;


import com.zjut.commonutils.R;
import com.zjut.staservice.client.UcenterClient;
import com.zjut.staservice.service.StatisticsDailyService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-07-27
 */
//@CrossOrigin  // 使用spring cloud gateway时此处不能加注解，会造成二次跨域
@RestController
@RequestMapping("/staservice/statisticsdaily")
public class StatisticsDailyController {



    @Autowired
    private StatisticsDailyService statisticsDailyService;

    // 统计当天注册的人数以及其他信息，并且在数据库中插入数据记录
    @ApiOperation(value = "统计当天注册的人数")
    @GetMapping("/countRegisterNum/{day}")
    public R countRegisterNum(@PathVariable String day){
        int count = statisticsDailyService.getRegisterNum(day);
        statisticsDailyService.createStatisticsByDay(day);
        return R.ok().data("registerNum",count);
    }

    @ApiOperation(value = "根据前端传递的起止日期与统计数据类型返回数据")
    @GetMapping("showChart/{startDay}/{endDay}/{dataType}")
    public R showCharts(
            @ApiParam(name = "startDay", value = "统计数据起始日期", required = true)
            @PathVariable String startDay,
                        @ApiParam(name = "endDay", value = "统计数据截至日期", required = true)
                        @PathVariable String endDay,
                        @ApiParam(name = "dataType", value = "统计数据类型", required = true)
                        @PathVariable String dataType){
        Map<String, Object> map = statisticsDailyService.showChartsDatas(startDay,endDay,dataType);
        return R.ok().data(map);
    }

}

