package com.bupt.ecommercestreamingsystem;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.web.socket.config.annotation.EnableWebSocket;

@SpringBootApplication
@EnableCaching // 开启缓存
@EnableWebSocket // 开启 WebSocket
@MapperScan("com.bupt.ecommercestreamingsystem.*.mapper")
public class ECommerceStreamingSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(ECommerceStreamingSystemApplication.class, args);
    }

}
