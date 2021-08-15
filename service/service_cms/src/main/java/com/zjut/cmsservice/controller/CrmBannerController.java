package com.zjut.cmsservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zjut.cmsservice.client.CrmClient;
import com.zjut.cmsservice.entity.CrmBanner;
import com.zjut.cmsservice.entity.vo.BannerQuery;
import com.zjut.cmsservice.service.CrmBannerService;
import com.zjut.commonutils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 首页banner表 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-07-19
 */
@Api("banner后端管理控制层")
@RestController
@RequestMapping("/educms/banner")
//@CrossOrigin  // 使用spring cloud gateway时此处不能加注解，会造成二次跨域
public class CrmBannerController {

    @Autowired
    private CrmBannerService crmBannerService;

    @Autowired
    private CrmClient crmClient;

    @ApiOperation(value = "获取前八张最新的banner")
    @GetMapping("/getBannerLastly")
    public R getBannerLastly(){
        List<CrmBanner> bannerList = crmBannerService.getLastly();
        return R.ok().data("list",bannerList);
    }

    @ApiOperation(value = "前台上传banner到阿里云oss")
    @PostMapping("/uploadBanners")
    public R uploadBanners( MultipartFile file){
        System.out.println("前台banner上传到阿里云oss");

        // 远程调用oss服务的接口实现上传banner
        String url = crmClient.uploadBanner(file);
        System.out.println("banner下载地址：" + url);
        return R.ok().data("url",url);
    }




    @ApiOperation(value = "获取banner分页列表")
    @GetMapping("/getBannerPage/{current}/{limit}")
    public R getBannerPage(
            @ApiParam(name = "current", value = "当前页码", required = true)
            @PathVariable Long current,
            @ApiParam(name = "limit", value = "每页记录条数",required = true)
            @PathVariable Long limit,
            @RequestBody(required = false) BannerQuery bannerQuery){

        Page<CrmBanner> page = new Page<>(current,limit);
        QueryWrapper<CrmBanner> wrapper = new QueryWrapper<>();
        if (bannerQuery != null){
            String title = bannerQuery.getTitle();
            Date begin = bannerQuery.getBegin();
            Date end = bannerQuery.getEnd();

            if (! StringUtils.isEmpty(title) && title != ""){
                wrapper.like("title", title);
            }
            if (!StringUtils.isEmpty(begin)){
                wrapper.ge("gmt_create", begin);
            }
            if (!StringUtils.isEmpty(end)){
                wrapper.le("gmt_create", end);
            }
            wrapper.orderByDesc("gmt_create");
        }else {
            System.out.println("传入的banner对象为空");
        }

        crmBannerService.page(page,wrapper);

        long total = page.getTotal();
        List<CrmBanner> rows = page.getRecords();
        Map map =new HashMap();
        map.put("total", total);
        map.put("list", rows);
        System.out.println("查询到的banner的总数：" + total + "查询的banner列表：" + rows);

        return R.ok().data(map);
    }

    @ApiOperation(value = "增加banner")
    @PostMapping("/addBanner")
    public R addPage(@RequestBody CrmBanner crmBanner){
        crmBannerService.save(crmBanner);
        return R.ok();
    }

    // 删除数据时清空banner中所有缓存
    @CacheEvict(value = "banner", allEntries = true)
    @ApiOperation(value = "根据id删除banner")
    @DeleteMapping("/removeBanner/{id}")
    public R removeBanner(
            @ApiParam(name = "id", value = "banner的id", required = true)
            @PathVariable String id){
        crmBannerService.removeById(id);
        System.out.println("数据库中已删除banner记录");
        return R.ok();
    }

    // 修改数据也要清空缓存中的元数据
    @CacheEvict(value = "banner", allEntries = true)
    @ApiOperation(value = "修改banner")
    @PutMapping("/updateBanner")
    public R updateBanner(@RequestBody CrmBanner crmBanner){

        crmBannerService.update(crmBanner,null);
        return R.ok();
    }


    @ApiOperation(value = "根据id查询banner")
    @GetMapping("/getBanner/{id}")
    public R getBannerById(
            @ApiParam(name = "id", value = "banner的id",required = true)
            @PathVariable String id){
        CrmBanner banner = crmBannerService.getById(id);
        return R.ok().data("banner",banner);
    }





}







