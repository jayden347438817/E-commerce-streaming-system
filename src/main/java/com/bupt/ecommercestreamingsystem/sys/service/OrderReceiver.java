package com.bupt.ecommercestreamingsystem.sys.service;

import com.bupt.ecommercestreamingsystem.sys.entity.Orders;
import com.bupt.ecommercestreamingsystem.sys.entity.Products;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//订单接受者
@Service
public class OrderReceiver {

    @Autowired
    private IOrdersService ordersService;
    @Autowired
    private IProductsService productsService;

    // 接收创建订单
    @RabbitListener(queues = "orderQueue")
    @RabbitHandler
    public void processOrder(Orders order) {
        System.out.println("OrderReceiver消费者收到创建订单消息  : " + order.toString());
        // 这里可以添加处理新订单的逻辑，例如判断库存
        // 如果库存不足，可以调用撤销订单的方法
        int productId = order.getProductId();
        int quantity = order.getQuantity();
        Products product = productsService.getById(productId);
        if (product.getQuantity() < quantity) {// 库存不足
            ordersService.cancelOrder(order.getId());// 调用撤销订单的方法
        }
    }

    // 过期订单
    @RabbitListener(queues = "orderDelayQueue")
    @RabbitHandler
    public void processDelayOrder(Orders order) {
        System.out.println("OrderReceiver消费者收到订单过期消息  : " + order.toString());
        // 检查订单状态，如果订单的状态还是"待支付"，则更新为"已过期"
        if ("待支付".equals(order.getStatus())) {
            order.setStatus("已过期");
            ordersService.updateById(order);
            System.out.println("订单已过期，订单信息：" + order.toString());
        }
    }

    // 撤销订单
    @RabbitListener(queues = "orderCancelQueue")
    @RabbitHandler
    public void processCancelOrder(Orders order) {
        System.out.println("OrderReceiver消费者收到撤销订单消息  : " + order.toString());
        // 检查订单状态，如果订单的状态还是"待支付"，则更新为"已撤销"
        if ("待支付".equals(order.getStatus())) {
            order.setStatus("已撤销");
            ordersService.updateById(order);
        }
    }

}
