package com.atguigu.springcloud.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @Author zouyang666
 * @Date 2020/4/1 19:50
 */
@Configuration
public class ApplicationContextConfig {

    @Bean
//    @LoadBalanced//开启RestTemplate负载均衡
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }
}
