package com.bupt.ecommercestreamingsystem;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.bupt.ecommercestreamingsystem.*.mapper")
public class ECommerceStreamingSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(ECommerceStreamingSystemApplication.class, args);
    }

}
