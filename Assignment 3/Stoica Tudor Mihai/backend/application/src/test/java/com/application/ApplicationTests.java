package com.application;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan({"com.core"})
@ComponentScan(basePackages = {"com.infrastructure"})
@SpringBootTest
class ApplicationTests {

    @Test
    void contextLoads() {
    }

}
