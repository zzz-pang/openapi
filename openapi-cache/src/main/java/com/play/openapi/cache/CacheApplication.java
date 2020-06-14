package com.play.openapi.cache;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class CacheApplication {
    public static void main(String[]args){
        SpringApplication.run(CacheApplication.class,args);
    }


}
