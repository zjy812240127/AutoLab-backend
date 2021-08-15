package com.zjut.startup.security.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class Member {

    @ApiModelProperty(value = "管理员id")
    private String id;
    @ApiModelProperty(value = "管理员昵称")
    private String name;
    @ApiModelProperty(value = "管理员手机")
    private String mobile;
    @ApiModelProperty(value = "管理员密码")
    private String password;

}
