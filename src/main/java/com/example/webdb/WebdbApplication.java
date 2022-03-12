package com.example.webdb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
@ImportResource("classpath:applicationContext.xml")
public class WebdbApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebdbApplication.class, args);
    }

}
