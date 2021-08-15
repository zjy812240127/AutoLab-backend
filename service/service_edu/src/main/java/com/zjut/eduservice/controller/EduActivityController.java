package com.zjut.eduservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zjut.commonutils.R;
import com.zjut.eduservice.entity.EduActivity;
import com.zjut.eduservice.entity.vo.ActivityQuery;
import com.zjut.eduservice.service.EduActivityService;
import com.zjut.serviceBase.exceptionHandler.MyException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.bytebuddy.implementation.bytecode.Throw;
import org.omg.CORBA.PUBLIC_MEMBER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-08-10
 */
@RestController
@RequestMapping("/eduservice/activity")
public class EduActivityController {

    @Autowired
    private EduActivityService eduActivityService;

    @ApiOperation("查询所有活动")
    @GetMapping("/getAll")
    public R getAllActivity(){
        List<EduActivity> activityList = eduActivityService.list(null);
        return R.ok().data("list",activityList);
    }

    @ApiOperation("首页查询最新的八个活动")
    @GetMapping("/getHotActivity")
    public R getHotActivity(){

        List<EduActivity> eduActivityList = eduActivityService.getHot();

        return R.ok().data("list",eduActivityList);
    }

    @ApiOperation("根据id查询单个活动的信息")
    @GetMapping("/selectById/{activityId}")
    public R getById(
            @ApiParam(name = "activityId",value = "要查询的活动的id",required = true)
            @PathVariable String activityId){

        EduActivity activity = eduActivityService.getById(activityId);
        System.out.println("根据id获取活动信息");

        return R.ok().data("activity",activity);
    }

    @ApiOperation("分页查询")
    @GetMapping("/getPageList/{current}/{limit}")
    public R getPageList(@ApiParam(name = "current",value = "当前页")
                         @PathVariable long current,
                         @ApiParam(name = "limit", value = "每页记录条数")
                         @PathVariable long limit){
        Page<EduActivity> page = new Page<>(current,limit);
        QueryWrapper<EduActivity> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("gmt_create");
        eduActivityService.page(page, wrapper);
        List<EduActivity> eduActivityList = page.getRecords();
        long total = page.getTotal();
        long activityPage = page.getPages();
        long pageCurrent = page.getCurrent();
        boolean hasNext = page.hasNext();
        boolean hasPrevious = page.hasPrevious();

        Map map = new HashMap();
        map.put("total",total);
        map.put("list",eduActivityList);
        map.put("hasNext",hasNext);
        map.put("hasPrevious",hasPrevious);
        map.put("current",current);
        map.put("pages",pageCurrent);
        return R.ok().data(map);
    }



    @ApiOperation("根据条件分页查询")
    @PostMapping("/getByConditions/{current}/{limit}")
    public R getByConditions(
            @ApiParam(name = "current", value = "当前页", required = true)
            @PathVariable long current,
            @ApiParam(name = "limit", value = "每页记录条数", required = true)
                             @PathVariable long limit,
            @RequestBody(required = false) ActivityQuery activityQuery) {
        Page<EduActivity> page = new Page<>(current,limit);
        QueryWrapper<EduActivity> wrapper = new QueryWrapper<>();
        final String title = activityQuery.getTitle();
        final String begin = activityQuery.getBegin();
        final String end = activityQuery.getEnd();
        if (null != title){
            wrapper.like("title",title);
        }
        if (null != begin){
            wrapper.ge("gmt_create",begin);
        }
        if (null != end){
            wrapper.le("gmt_create",end);
        }
        wrapper.orderByDesc("gmt_create");
        eduActivityService.page(page,wrapper);
        long total = page.getTotal();
        List<EduActivity> activityList = page.getRecords();
        Map map = new HashMap();
        map.put("total",total);
        map.put("list",activityList);
        return R.ok().data(map);
    }



    @ApiOperation("添加活动信息")
    @PostMapping("/addActivity")
    public R addActivity(@RequestBody EduActivity eduActivity){
        boolean save = eduActivityService.save(eduActivity);
        if (save){
            return R.ok();
        }else {
            throw new MyException(20001,"添加活动失败");
        }
    }

    @ApiOperation("修改活动信息")
    @PostMapping("/alterActivity")
    public R modifyActivity(@RequestBody EduActivity eduActivity){
        boolean update = eduActivityService.update(eduActivity,null);
        if(!update){
            throw new MyException(20001,"修改活动信息失败");
        }
        return R.ok();
    }

    @ApiOperation("根据id删除活动信息，包括删除oss中的图片以及阿里云中的视频")
    @DeleteMapping("/deleteActivity/{activityId}")
    public R deleteActivity(
            @ApiParam(name = "activityId",value = "要删除的活动的id", required = true)
            @PathVariable String activityId){

        eduActivityService.deleteById(activityId);

        return R.ok();
    }





}

