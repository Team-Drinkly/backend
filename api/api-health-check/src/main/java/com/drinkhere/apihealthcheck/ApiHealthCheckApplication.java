package com.drinkhere.apihealthcheck;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.drinkhere")
public class ApiHealthCheckApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiHealthCheckApplication.class, args);
    }

}
