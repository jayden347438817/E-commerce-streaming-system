package com.bupt.ecommercestreamingsystem.handler;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.concurrent.CopyOnWriteArrayList;
@Component
public class WebSocketHandler extends TextWebSocketHandler {
    // 使用一个线程安全的集合来存储 WebSocketSession
    private static CopyOnWriteArrayList<WebSocketSession> sessions = new CopyOnWriteArrayList<>();

    // 处理收到的消息
    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException {
        // Broadcast the message to all connected sessions
        for (WebSocketSession webSocketSession : sessions) {
            if (webSocketSession.isOpen()) {
                webSocketSession.sendMessage(message);
            }
        }
    }

    // 处理新建连接
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        // connection was established, add the session to your custom session holder
        sessions.add(session);
        System.out.println("New connection: " + session.getId());
    }

    // 处理连接关闭
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        //
        sessions.remove(session);
    }
}
