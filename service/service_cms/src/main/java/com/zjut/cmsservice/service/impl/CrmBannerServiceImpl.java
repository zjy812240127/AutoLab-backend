package com.zjut.cmsservice.service.impl;

import com.zjut.cmsservice.entity.CrmBanner;
import com.zjut.cmsservice.mapper.CrmBannerMapper;
import com.zjut.cmsservice.service.CrmBannerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2021-07-19
 */
@Service
public class CrmBannerServiceImpl extends ServiceImpl<CrmBannerMapper, CrmBanner> implements CrmBannerService {

    // 启用redis，返回结果存入缓存，每次get数据时先从banner缓存中读取，缓存值key为selectIndexList
    @Cacheable(value = "banner", key = "'selectBannerList'")
    @Override
    public List<CrmBanner> selectBannerList() {
        // 获取所有banner列表
        List<CrmBanner> bannerList = baseMapper.selectList(null);
        return bannerList;
    }

    @Override
    public List<CrmBanner> getLastly() {

        List<CrmBanner> bannerList = baseMapper.getBannerList();
        return bannerList;

    }
}
