package com.zjut.staservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zjut.staservice.client.UcenterClient;
import com.zjut.staservice.entity.StatisticsDaily;
import com.zjut.staservice.mapper.StatisticsDailyMapper;
import com.zjut.staservice.service.StatisticsDailyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2021-07-27
 */
@Service
public class StatisticsDailyServiceImpl extends ServiceImpl<StatisticsDailyMapper, StatisticsDaily> implements StatisticsDailyService {

    @Autowired
    private UcenterClient ucenterClient;
    @Autowired
    private StatisticsDailyService statisticsDailyService;

    // 获取当天注册的人数
    @Override
    public int getRegisterNum(String day) {
        int registerNum = ucenterClient.registerNum(day);

        return registerNum;
    }

    @Override
    public void createStatisticsByDay(String day) {
        // 前端按一定时间间隔查询本天数据访问量（注册人数，登录人数，观看视频人数，课程数）
        // 每次统计要删除已有的统计数据，进行覆盖
        QueryWrapper<StatisticsDaily> wrapper = new QueryWrapper<>();
        wrapper.eq("date_calculated",day);
        int delete = baseMapper.delete(wrapper);

        // 调用远程接口查询当天截至目前的统计数据，方法与统计当天注册人数一致，此处使用随机数代替注册人数之外的数据
        // 注册人数
        int registerNum = ucenterClient.registerNum(day);
        int loginNum = RandomUtils.nextInt(100, 500);
        int videoNum = RandomUtils.nextInt(50, 200);
        int courseNum = RandomUtils.nextInt(200, 300);
        // 新建一个统计对象，将以上查询数据放入
        StatisticsDaily statisticsDaily = new StatisticsDaily();
        statisticsDaily.setVideoViewNum(videoNum);
        statisticsDaily.setRegisterNum(registerNum);
        statisticsDaily.setLoginNum(loginNum);
        statisticsDaily.setDateCalculated(day);
        statisticsDaily.setCourseNum(courseNum);
        // 插入新纪录
        int insert = baseMapper.insert(statisticsDaily);
        System.out.println("添加的统计记录条数=" + insert);
    }

    @Override
    public Map<String, Object> showChartsDatas(String startDay, String endDay, String dataType) {
        // 查询起始日期到终止日期之间有统计记录的日期（并不是每天都有统计数据）
        Map<String,Object> map = new HashMap<>();
        QueryWrapper<StatisticsDaily> wrapper = new QueryWrapper<>();
        wrapper.between("date_calculated", startDay, endDay);
        List<StatisticsDaily> statisticsDailyList = baseMapper.selectList(wrapper);
        // 存放日期
        List<String> dateList = new ArrayList<>();
        for (int i=0; i<statisticsDailyList.size(); i++){
            dateList.add(statisticsDailyList.get(i).getDateCalculated());
        }
        // 存入图表的横轴x，也就是统计日期
        map.put("dateList",dateList);
        // 查询每条记录的相应数据
        List<Integer> dataList = new ArrayList<>();
        for(int j=0; j<statisticsDailyList.size(); j++){
            StatisticsDaily daily = statisticsDailyList.get(j);
            switch (dataType){
                case "register_num":
                    dataList.add(daily.getRegisterNum());
                    break;
                case "login_num":
                    dataList.add(daily.getLoginNum());
                    break;
                case "video_view_num":
                    dataList.add(daily.getVideoViewNum());
                    break;
                case "course_num":
                    dataList.add(daily.getCourseNum());
                    break;
                default:
                    break;
            }
        }
        map.put("dataList",dataList);
        return map;
    }
}
