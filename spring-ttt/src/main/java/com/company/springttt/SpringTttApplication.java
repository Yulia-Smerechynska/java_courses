package com.company.springttt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
public class SpringTttApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringTttApplication.class, args);
    }

}
