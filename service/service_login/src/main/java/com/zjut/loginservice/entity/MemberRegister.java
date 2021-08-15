package com.zjut.loginservice.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel(value = "前端注册时传递邮箱的对象，因为url中无法解析“.”等特殊符号")
public class MemberRegister {

    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private String id;
    private String name;
    private String email;
    private String code;
    private String password;

}
