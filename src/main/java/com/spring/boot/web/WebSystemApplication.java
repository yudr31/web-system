package com.spring.boot.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import tk.mybatis.spring.annotation.MapperScan;

@EnableEurekaClient
@EnableFeignClients(basePackages = {"com.spring.boot"})
@SpringBootApplication(scanBasePackages = "com.spring.boot")
@MapperScan("com.spring.boot.web.mapper")
public class WebSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebSystemApplication.class, args);
	}

}
