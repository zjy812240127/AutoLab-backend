package com.zjut.eduservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author Joya Zou
 * @date 2021年06月27日 14:19
 */

@SpringBootApplication
@EnableDiscoveryClient  // 启动nacos
@EnableFeignClients  // 分布式跨服务调用
@ComponentScan(basePackages = {"com.zjut"})  // 扫描父工程下所有工程中以com.zjut开头的包中的bean
public class EduApplication {

    public static void main(String[] args) {
        SpringApplication.run(EduApplication.class, args);
    }

}
