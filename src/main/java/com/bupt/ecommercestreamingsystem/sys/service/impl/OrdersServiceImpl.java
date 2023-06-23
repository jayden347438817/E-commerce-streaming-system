package com.bupt.ecommercestreamingsystem.sys.service.impl;

import com.bupt.ecommercestreamingsystem.sys.entity.Orders;
import com.bupt.ecommercestreamingsystem.sys.entity.Products;
import com.bupt.ecommercestreamingsystem.sys.mapper.OrdersMapper;
import com.bupt.ecommercestreamingsystem.sys.service.IOrdersService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bupt.ecommercestreamingsystem.sys.service.OrderSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Jayden
 * @since 2023-05-25
 */
@Service
public class OrdersServiceImpl extends ServiceImpl<OrdersMapper, Orders> implements IOrdersService {

    @Autowired
    private OrderSender orderSender;
    @Autowired
    private ProductsServiceImpl productsService;

    // 创建订单
    @Override
    public void createOrder(Integer userId, Integer productId, Integer count) {
        Orders orders = new Orders();
        orders.setUserId(userId);
        orders.setProductId(productId);
        orders.setQuantity(count);
        orders.setStatus("待支付");
        orders.setTimestamp(LocalDateTime.now());
        this.save(orders);
        // 发送订单到队列
        orderSender.sendOrder(orders);
        // 发送延迟消息 5min
        orderSender.sendDelayOrder(orders, 1000 * 60 * 5);
    }

    // 撤销订单
    @Override
    public void cancelOrder(Integer orderId) {
        Orders orders = this.getById(orderId);
        if (orders != null && orders.getStatus().equals("待支付")){
            orderSender.sendOrderCancel(orders);
        }
    }

    // 支付订单
    @Override
    public void payOrder(Integer orderId) {
        Orders orders = this.getById(orderId);
        if (orders != null && orders.getStatus().equals("待支付")){// 订单存在且未支付
            // 订单不能重复支付，通过订单状态判断实现
            orders.setStatus("已支付");
            this.updateById(orders);
            // 处理商品库存
            Products products = productsService.getById(orders.getProductId());
            products.setQuantity(products.getQuantity() - orders.getQuantity());
            productsService.updateById(products);
        } else {
            System.out.println("订单不存在或已支付");
        }
    }


}
