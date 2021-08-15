package com.zjut.cmsservice.api;


import com.zjut.cmsservice.entity.CrmBanner;
import com.zjut.cmsservice.service.CrmBannerService;
import com.zjut.commonutils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api("网站首页banner列表")
@RestController
//@CrossOrigin
@RequestMapping("/educms/banner")
@Transactional
public class BannerApiController {

    @Autowired
    private CrmBannerService crmBannerService;

    @ApiOperation(value = "获取首页banner列表")
    @GetMapping("/getBannerList")
    public R getBannerList(){
        System.out.println("查询banner列表");

        List<CrmBanner> bannerList = crmBannerService.selectBannerList();
        System.out.println("banner列表为" +  bannerList);

        return R.ok().data("list",bannerList);
    }


}
