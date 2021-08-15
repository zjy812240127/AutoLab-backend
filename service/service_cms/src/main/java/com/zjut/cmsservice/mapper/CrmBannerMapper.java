package com.zjut.cmsservice.mapper;

import com.zjut.cmsservice.entity.CrmBanner;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 首页banner表 Mapper 接口
 * </p>
 *
 * @author testjava
 * @since 2021-07-19
 */
public interface CrmBannerMapper extends BaseMapper<CrmBanner> {

    List<CrmBanner> getBannerList();

}
