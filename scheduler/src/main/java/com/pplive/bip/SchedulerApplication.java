package com.pplive.bip;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Created by jiatingjin on 2018/1/2.
 */
@SpringBootApplication
@EnableScheduling
@EnableCaching
public class SchedulerApplication {
    public static void main(String[] args) throws Exception {
        SpringApplication.run(SchedulerApplication.class, args);
    }
}
