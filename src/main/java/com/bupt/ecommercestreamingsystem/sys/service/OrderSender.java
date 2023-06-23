package com.bupt.ecommercestreamingsystem.sys.service;

import com.bupt.ecommercestreamingsystem.sys.entity.Orders;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// 订单发送
@Service
public class OrderSender {

    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public OrderSender(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    // 发送订单
    public void sendOrder(Orders orders){
        rabbitTemplate.convertAndSend("orderQueue",orders);
    }

    // 延迟发送（过期）
    public void sendDelayOrder(Orders orders, int delayTimes){
        // 设置延迟时间（5min）
        rabbitTemplate.convertAndSend("delayedExchange", "orderDelayQueue", orders, message -> {
            message.getMessageProperties().setDelay(delayTimes);
            return message;
        });

    }

    // 撤销订单
    public void sendOrderCancel(Orders orders) {
        rabbitTemplate.convertAndSend("orderCancelQueue",orders);
    }
}
