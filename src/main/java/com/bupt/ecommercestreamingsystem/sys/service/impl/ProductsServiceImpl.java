package com.bupt.ecommercestreamingsystem.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.bupt.ecommercestreamingsystem.sys.entity.Products;
import com.bupt.ecommercestreamingsystem.sys.mapper.ProductsMapper;
import com.bupt.ecommercestreamingsystem.sys.service.IProductsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
        return data;
    }

    @Override
    public Map<String, Object> getProductById(Integer productId) {
        Products products = this.getById(productId);
        if (products == null) {
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

    @Override
    public List<?> getAllProducts() {
        List<Products> products = this.list();
        return products;
    }

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

    @Override
    public void deleteProduct(Integer productId) {
        this.removeById(productId);
    }


}
