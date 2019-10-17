package com.aliCloud.api;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Author: GoodMan
 * Date: 2019/10/15
 * Description:
 */
@SpringBootApplication
@EnableDiscoveryClient
@MapperScan(basePackages = {"com.aliCloud.api.mapper"})
public class UserLoginApplication2 {

    public static void main(String[] args) {
        SpringApplication.run(UserLoginApplication2.class, args);
    }
}
