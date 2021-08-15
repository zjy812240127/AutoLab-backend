package com.zjut.fileservice.utils;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author Joya Zou
 * @date 2021年07月01日 8:59
 */
@Component  // 交给spring管理
// InitializingBean用来启动项目后，自动执行其中的方法
public class ConstantPropertiesUtils implements InitializingBean {

    // 读取配置文件的数据
    @Value("${aliyun.oss.file.endpoint}")
    private String endPoint;
    @Value("${aliyun.oss.file.keyid}")
    private String keyId;
    @Value("${aliyun.oss.file.keysecret}")
    private String keySecret;
    @Value("${aliyun.oss.file.bucketname}")
    private String bucketName;

    // 定义一个公开的静态常量
    public static String END_POINT;
    public static String ACCESS_KEY_ID;
    public static String ACCESS_KEY_SECRET;
    public static String BUCKET_NAME;

    // 项目启动完，并加载完以上的属性后会执行该方法
    @Override
    public void afterPropertiesSet() throws Exception {

        END_POINT = endPoint;
        ACCESS_KEY_ID = keyId;
        ACCESS_KEY_SECRET = keySecret;
        BUCKET_NAME = bucketName;
    }
}
