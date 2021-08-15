package com.zjut.cmsservice.service;

import com.zjut.cmsservice.entity.CrmBanner;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务类
 * </p>
 *
 * @author testjava
 * @since 2021-07-19
 */
public interface CrmBannerService extends IService<CrmBanner> {

    List<CrmBanner> selectBannerList();

    List<CrmBanner> getLastly();

}
