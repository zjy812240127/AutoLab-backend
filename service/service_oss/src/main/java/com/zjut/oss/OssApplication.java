package com.zjut.oss;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author Joya Zou
 * @date 2021年07月01日 8:45
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)  // 使得启动类不加载数据库，防止报错
@ComponentScan(basePackages = {"com.zjut"})
@EnableDiscoveryClient
@EnableFeignClients
public class OssApplication {

    public static void main(String[] args) {
        SpringApplication.run(OssApplication.class,args);
    }

}
