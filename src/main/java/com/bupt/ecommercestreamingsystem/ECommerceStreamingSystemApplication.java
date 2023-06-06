package com.bupt.ecommercestreamingsystem;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching // 开启缓存
@MapperScan("com.bupt.ecommercestreamingsystem.*.mapper")
public class ECommerceStreamingSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(ECommerceStreamingSystemApplication.class, args);
    }

}
