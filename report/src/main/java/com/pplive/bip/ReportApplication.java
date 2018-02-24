package com.pplive.bip;

import com.pplive.bip.exception.ApiHandlerExceptionResolver;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.HandlerExceptionResolver;


@SpringBootApplication
@EnableCaching
@EnableEurekaClient
@EnableFeignClients
public class ReportApplication {

    @Bean
    HandlerExceptionResolver customExceptionResolver () {
        return new ApiHandlerExceptionResolver();
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(ReportApplication.class, args);
    }
}