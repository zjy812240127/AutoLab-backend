package com.zjut.vod;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author Joya Zou
 * @date 2021年07月08日 8:44
 */
// 不访问数据库
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@ComponentScan(basePackages = {"com.zjut"})
@EnableDiscoveryClient
public class VodApplication {


    public static void main(String[] args) {
        SpringApplication.run(VodApplication.class,args);
    }





}
