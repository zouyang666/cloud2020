package com.atguigu.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @Author zouyang666
 * @Date 2020/4/2 11:47
 */
@SpringBootApplication
@EnableDiscoveryClient
public class OrderZKMian80 {
    public static void main(String[] args) {
        SpringApplication.run(OrderZKMian80.class,args);
    }
}
