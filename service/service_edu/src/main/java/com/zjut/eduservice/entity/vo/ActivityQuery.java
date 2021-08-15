package com.zjut.eduservice.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;

@Data
public class ActivityQuery {

    @ApiModelProperty(value = "活动标题")
    private String title;
    @ApiModelProperty(value = "查询范围开始时间", example = "2019-01-01 10:10:10")
    private String begin;
    @ApiModelProperty(value = "查询范围结束时间", example = "2019-01-01 10:10:10")
    private String end;
}
