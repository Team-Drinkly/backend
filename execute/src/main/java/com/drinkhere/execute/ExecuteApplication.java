package com.drinkhere.execute;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.drinkhere")
public class ExecuteApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExecuteApplication.class, args);
    }

}