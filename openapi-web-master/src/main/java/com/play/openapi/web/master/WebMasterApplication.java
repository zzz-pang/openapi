package com.play.openapi.web.master;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
@MapperScan(basePackages = "com.play.openapi.web.master.mapper")
public class WebMasterApplication {

  public static void main(String[]args){
      SpringApplication.run(WebMasterApplication.class,args);
  }

}
