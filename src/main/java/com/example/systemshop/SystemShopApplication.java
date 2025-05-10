package com.example.systemshop;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.example.systemshop.mapper")
public class SystemShopApplication {
    public static void main(String[] args) {
        SpringApplication.run(SystemShopApplication.class, args);
    }
} 