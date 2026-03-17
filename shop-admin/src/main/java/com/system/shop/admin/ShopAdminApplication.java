package com.system.shop.admin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@MapperScan({"com.system.shop.service.mapper", "com.system.shop.admin.mapper"})
@ComponentScan(basePackages = {"com.system.shop.admin", "com.system.shop.service", "com.system.shop.common"})
@EnableAsync
public class ShopAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShopAdminApplication.class, args);
    }

}
