package com.zjut.staservice.schedule;

import com.zjut.staservice.service.StatisticsDailyService;
import com.zjut.staservice.utils.DateUtil;
import io.swagger.annotations.Api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Api("定时工具类，按照一定的时间间隔执行方法，cron语句生成可以参考网站：http://cron.qqe2.com/")
@Component
public class ScheduleTask {

    @Autowired
    private StatisticsDailyService statisticsDailyService;

    /**
     * 测试
     *  每天七点到二十三点每五秒执行一次
     */
    //@Scheduled(cron = "0/5 * * * * ?")
    //public void task1() {
    //    System.out.println("*********++++++++++++*****执行了");
    //}

    /**
     * 每天凌晨1点执行定时
     */
    @Scheduled(cron = "0 0 1 * * ?")
    public void task2() {
        //获取上一天的日期
        String day = DateUtil.formatDate(DateUtil.addDays(new Date(), -1));
        statisticsDailyService.createStatisticsByDay(day);
    }


}
