package com.bupt.ecommercestreamingsystem.sys.service;

import com.bupt.ecommercestreamingsystem.sys.entity.Orders;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Jayden
 * @since 2023-05-25
 */
public interface IOrdersService extends IService<Orders> {

    void createOrder(Integer userId, Integer productId, Integer quantity);


    void cancelOrder(Integer orderId);

    void payOrder(Integer orderId);
}
