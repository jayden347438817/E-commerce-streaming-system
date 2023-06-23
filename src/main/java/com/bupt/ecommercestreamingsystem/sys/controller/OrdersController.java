package com.bupt.ecommercestreamingsystem.sys.controller;

import com.bupt.ecommercestreamingsystem.common.vo.Result;
import com.bupt.ecommercestreamingsystem.sys.entity.Orders;
import com.bupt.ecommercestreamingsystem.sys.service.IOrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Jayden
 * @since 2023-05-25
 */
@RestController
@RequestMapping("/orders")
public class OrdersController {
    @Autowired
    private IOrdersService ordersService;

    //创建订单
    @PostMapping("")
    public Result<?> createOrder(@RequestParam("userId") Integer userId,
                                 @RequestParam("productId") Integer productId,
                                 @RequestParam("quantity") Integer quantity){
        ordersService.createOrder(userId,productId,quantity);//创建订单并使用消息队列发送订单
        return Result.success("创建订单成功");
    }

    //查询订单
    @GetMapping("/{orderId}")
    public Result<?> getOrderById(@PathVariable("orderId") Integer orderId){
        Orders orders = ordersService.getById(orderId);
        if (orders == null){
            return Result.fail("订单不存在");
        }
        return Result.success(orders);
    }

    //撤销未支付订单
    @DeleteMapping("/{orderId}")
    public Result<?> cancelOrder(@PathVariable("orderId") Integer orderId){
        ordersService.cancelOrder(orderId);
        Orders orders = ordersService.getById(orderId);
        if (orders == null){
            return Result.fail("订单不存在");
        }
        if (orders.getStatus().equals("已支付")){
            return Result.fail("订单已支付，无法撤销");
        }
        return Result.success("撤销订单成功");
    }

    //支付订单
    @PostMapping("/pay")
    public Result<?> payOrder(@RequestParam("orderId") Integer orderId){
        ordersService.payOrder(orderId);
        return Result.success("支付订单成功");
    }

}
