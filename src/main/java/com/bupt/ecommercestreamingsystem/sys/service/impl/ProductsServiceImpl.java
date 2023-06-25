package com.bupt.ecommercestreamingsystem.sys.service.impl;

import com.alibaba.fastjson2.JSON;
import com.bupt.ecommercestreamingsystem.sys.entity.Products;
import com.bupt.ecommercestreamingsystem.sys.entity.Users;
import com.bupt.ecommercestreamingsystem.sys.mapper.ProductsMapper;
import com.bupt.ecommercestreamingsystem.sys.service.IProductsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Jayden
 * @since 2023-05-25
 */
@Service
public class ProductsServiceImpl extends ServiceImpl<ProductsMapper, Products> implements IProductsService {

    @Autowired
    private RedisTemplate redisTemplate;

    // 添加商品
    @Override
    public Map<String, Object> addProduct(Integer ownerId, String name, String description, Integer quantity) {
        Products products = new Products();
        products.setOwnerId(ownerId);
        products.setName(name);
        products.setDescription(description);
        products.setQuantity(quantity);
        this.save(products);
        Map<String,Object> data = new HashMap<>();
        data.put("id",products.getId());
        data.put("ownerId",products.getOwnerId());
        data.put("name",products.getName());
        data.put("description",products.getDescription());
        data.put("quantity",products.getQuantity());
        return data;
    }

    // 删除商品，并利用redis的token验证用户身份
    @Override
    public Map<String, Object> getProductById(Integer productId, String token) {
        // 从redis中获取用户信息
        Object obj = redisTemplate.opsForValue().get(token);
        // 判断用户是否登录
        if (obj == null) {
            return null;
        }
        // 反序列化（用fastjson）
        Users loginUser = JSON.parseObject(JSON.toJSONString(obj),Users.class);
        Products products = this.getById(productId);
        if (products == null) {
            return null;
        }
        // 判断用户是否有权限,只能查看自己的商品
        if (!loginUser.getId().equals(this.getById(productId).getOwnerId())) {
            return null;
        }
        Map<String,Object> data = new HashMap<>();
        data.put("id",products.getId());
        data.put("ownerId",products.getOwnerId());
        data.put("name",products.getName());
        data.put("description",products.getDescription());
        data.put("quantity",products.getQuantity());
        return data;
    }

    // 获取全部商品列表
    @Override
    public List<?> getAllProducts() {
        List<Products> products = this.list();
        return products;
    }

    // 更新商品信息
    @Override
    public void updateProduct(Integer productId, Integer ownerId, String name, String description, Integer quantity) {
        Products products = new Products();
        products.setId(productId);
        products.setOwnerId(ownerId);
        products.setName(name);
        products.setDescription(description);
        products.setQuantity(quantity);
        this.updateById(products);
    }

    // 删除商品
    @Override
    public String deleteProduct(Integer productId, String token) {
        // 从redis中获取用户信息
        Object obj = redisTemplate.opsForValue().get(token);
        // 判断用户是否登录
        if (obj == null) {
            return null;
        }
        // 反序列化（用fastjson）
        Users loginUser = JSON.parseObject(JSON.toJSONString(obj),Users.class);
        // 判断用户是否有权限,只能删除自己的商品
        if (!loginUser.getId().equals(this.getById(productId).getOwnerId())) {
            return null;
        }
        this.removeById(productId);
        return("删除成功");
    }
}
