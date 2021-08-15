package com.zjut.cmsservice.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class BannerQuery {

    @ApiModelProperty(value = "条件查询的文件title")
    private String title;
    @ApiModelProperty(value = "条件查询的起始时间，查询在这个时间点之后创建的文件 ", example = "2019-01-01 10:10:10")
    private Date begin;
    @ApiModelProperty(value = "条件查询的终止时间，查询在这个时间点之前创建的文件", example = "2019-01-01 10:10:10")
    private Date end;
    @ApiModelProperty(value = "更新时传入的下载地址")
    private String imageUrl;
    @ApiModelProperty(value = "更新时传入的id")
    private String id;
    @ApiModelProperty(value = "更新时传入的详细信息")
    private String sort;


}
