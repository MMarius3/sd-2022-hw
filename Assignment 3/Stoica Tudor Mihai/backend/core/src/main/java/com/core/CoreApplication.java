package com.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
//@EnableJpaRepositories
//@ComponentScan({"mapper"})
//@ComponentScan({"service"})
//@ComponentScan({"repository"})
//@ComponentScan({"entity"})
//@EntityScan(basePackages = {"com/infrastructure/entity"})
public class CoreApplication {
    public static void main(String[] args) {
        SpringApplication.run(CoreApplication.class, args);
    }
}
