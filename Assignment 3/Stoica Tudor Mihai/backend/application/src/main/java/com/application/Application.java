package com.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.swing.*;

@SpringBootApplication
//@ComponentScan(basePackages = {"controller"})
@ComponentScan({"com.core"})
@ComponentScan(basePackages = {"com.infrastructure"})
//@EnableJpaRepositories
public class Application {

    public static void main(String[] args) {
//        SpringApplication.run(Application.class, args);

        new SpringApplicationBuilder(Application.class)
                .headless(false)
                .run(args);
    }
}
