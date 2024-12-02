package com.drinkhere.infras3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.drinkhere")
public class InfraS3Application {

    public static void main(String[] args) {
        SpringApplication.run(InfraS3Application.class, args);
    }

}
