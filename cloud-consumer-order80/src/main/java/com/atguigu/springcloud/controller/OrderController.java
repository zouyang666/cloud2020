package com.atguigu.springcloud.controller;

import com.atguigu.springcloud.entities.CommonResult;
import com.atguigu.springcloud.entities.Payment;
import com.atguigu.springcloud.lb.LoadBalancer;
import com.netflix.eureka.resources.InstancesResource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.net.URI;
import java.util.List;

/**
 * @Author zouyang666
 * @Date 2020/4/1 19:46
 */
@RestController
@Slf4j
public class OrderController {

//        public static final String PARMENT_URL="http://localhost:8001";
        public static final String PARMENT_URL="http://CLOUD-PAYMENT-SERVICE";
        @Resource
        private RestTemplate restTemplate;

        @Resource
        private LoadBalancer loadBalancer;
        @Resource
        private DiscoveryClient discoveryClient;


        @GetMapping("/consumer/payment/create")
        public CommonResult<Payment> create(Payment payment){
                return restTemplate.postForObject(PARMENT_URL+"/payment/create",payment,CommonResult.class);
        }
        @GetMapping("/consumer/payment/get/{id}")
        public CommonResult<Payment> getPayment(@PathVariable("id") Long id){
                return restTemplate.getForObject(PARMENT_URL+"/payment/get/"+id,CommonResult.class);
        }
        @GetMapping("/consumer/payment/getForEntity/{id}")
        public CommonResult<Payment> getPayment2(@PathVariable("id") Long id) {
                ResponseEntity<CommonResult> entity=restTemplate.getForEntity(PARMENT_URL+"/payment/get/"+id,CommonResult.class);
                if(entity.getStatusCode().is2xxSuccessful()){
                      return entity.getBody();
                }else {
                        return new CommonResult<>(444,"操作失败");
                }
        }
        @GetMapping(value = "/consumer/payment/lb")
        public String getPaymentLB(){
                List<ServiceInstance> instances=discoveryClient.getInstances("CLOUD-PAYMENT-SERVICE");
                if (instances==null || instances.size()<=0){
                        return null;
                }
                ServiceInstance serviceInstance=loadBalancer.instance(instances);
                URI uri = serviceInstance.getUri();
                return restTemplate.getForObject(uri+"/payment/lb",String.class);
        }


        /**
         * 链路跟踪 zipkin+sleuth
         * http://localhost/consumer/payment/zipkin
         *
         * @return
         */
        @GetMapping("/consumer/payment/zipkin")
        public String paymentZipkin() {
                return restTemplate.getForObject("http://localhost:8081/payment/zipkin/", String.class);
        }

}
