package com.bupt.ecommercestreamingsystem.config;

import com.bupt.ecommercestreamingsystem.handler.WebSocketHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

// WebSocket配置类
@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {
    // 注册WebSocket处理器
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(new WebSocketHandler(), "/websocket").setAllowedOrigins("*");// 允许跨域
    }
}

