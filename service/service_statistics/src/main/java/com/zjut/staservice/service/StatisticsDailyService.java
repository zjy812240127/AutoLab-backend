package com.zjut.staservice.service;

import com.zjut.staservice.entity.StatisticsDaily;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务类
 * </p>
 *
 * @author testjava
 * @since 2021-07-27
 */
public interface StatisticsDailyService extends IService<StatisticsDaily> {

    int getRegisterNum(String day);

    void createStatisticsByDay(String day);

    Map<String, Object> showChartsDatas(String startDay, String endDay, String dataType);
}
