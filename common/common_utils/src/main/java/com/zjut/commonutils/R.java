package com.zjut.commonutils;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Joya Zou
 * @date 2021年06月27日 16:17
 */

// 统一结果返回类

@Data  // lombok方法，自动生成set/get
public class R {

    // 返回的都是this或者r ---- 链式编程
    // r.ok().error().success()...   可以链式调用

    // 定义四种返回的数据类型
    @ApiModelProperty(value = "是否成功")
    private Boolean success;

    @ApiModelProperty(value = "返回码")
    private Integer code;

    @ApiModelProperty(value = "返回消息")
    private String message;

    @ApiModelProperty(value = "返回数据")
    private Map<String, Object> data = new HashMap<String, Object>();


    // 构造方法私有化，外部程序无法调用构造方法
    private R(){};

    // 操作成功结果的静态方法----------可以供外部程序调用
    public static R ok(){
        R r = new R();
        r.setCode(ResultCode.SUCCESS);  // 20000表示操作成功
        r.setMessage("操作成功");
        r.setSuccess(true);
        return r;
    }

    // 操作失败结果的静态方法----------可以供外部程序调用
    public static R error(){
        R r = new R();
        r.setCode(ResultCode.ERROR);  // 20000表示操作成功
        r.setMessage("操作失败");
        r.setSuccess(false);
        return r;
    }

    public R success(Boolean success){
        this.setSuccess(success);
        return this;
    }
    public R message(String message){
        this.setMessage(message);
        return this;
    }
    public R code(Integer code){
        this.setCode(code);
        return this;
    }
    public R data(String key, Object value){
        this.data.put(key, value);
        return this;
    }
    public R data(Map<String, Object> map){
        this.setData(map);
        return this;
    }


}
