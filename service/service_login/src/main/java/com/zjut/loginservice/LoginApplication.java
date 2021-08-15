package com.zjut.loginservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.CrossOrigin;

@SpringBootApplication
//@CrossOrigin
@EnableDiscoveryClient
@EnableFeignClients
@ComponentScan(basePackages = {"com.zjut"})
public class LoginApplication {

    public static void main(String[] args) {
        SpringApplication.run(LoginApplication.class,args);
    }

}
