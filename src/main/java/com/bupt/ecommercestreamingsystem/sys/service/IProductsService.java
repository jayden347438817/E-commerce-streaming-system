package com.bupt.ecommercestreamingsystem.sys.service;

import com.bupt.ecommercestreamingsystem.sys.entity.Products;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Jayden
 * @since 2023-05-25
 */
public interface IProductsService extends IService<Products> {

    Map<String, Object> addProduct(Integer ownerId, String name, String description, Integer quantity);

    Map<String, Object> getProductById(Integer productId, String token);

    List<?> getAllProducts();

    void updateProduct(Integer productId, Integer ownerId, String name, String description, Integer quantity);

    String deleteProduct(Integer productId, String token);
}
