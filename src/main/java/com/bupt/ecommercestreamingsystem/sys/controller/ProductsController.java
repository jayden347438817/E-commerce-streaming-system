package com.bupt.ecommercestreamingsystem.sys.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.bupt.ecommercestreamingsystem.common.vo.Result;
import com.bupt.ecommercestreamingsystem.sys.service.IProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Jayden
 * @since 2023-05-25
 */
@RestController
@RequestMapping("/products")
public class ProductsController {
    @Autowired
    private IProductsService productsService;

    // 添加商品
    @PostMapping("")
    public Result<?> addProduct(@RequestParam("ownerId") Integer ownerId,
                                @RequestParam("name") String name,
                                @RequestParam("description") String description,
                                @RequestParam("quantity") Integer quantity){
        Map<String,Object> data = productsService.addProduct(ownerId,name,description,quantity);
        return Result.success(data,"添加商品成功");
    }

    // 获取商品信息
    @Cacheable(value = "product",key = "#productId")
    @GetMapping("/{productId}")
    public Result<?> getProductById(@PathVariable("productId") Integer productId){
        Map<String,Object> data = productsService.getProductById(productId);
        if (data == null){
            return Result.fail(20004,"该商品不存在");
        }
        return Result.success(data,"获取商品成功");
    }

    // 获取全部商品列表
    @GetMapping("")
    public Result<?> getAllProducts(){
        List<?> products = productsService.getAllProducts();
        if (products == null){
            return Result.fail(20004,"商品列表为空");
        }
        return Result.success(products,"获取商品列表成功");
    }

    // 修改商品信息
    @CachePut(value = "product",key = "#productId")
    @PutMapping("/{productId}")
    public Result<?> updateProduct(@PathVariable(value = "productId") Integer productId,
                                   @RequestParam(value = "ownerId",required = false) Integer ownerId,
                                   @RequestParam(value = "name",required = false) String name,
                                   @RequestParam(value = "description",required = false) String description,
                                   @RequestParam(value = "quantity",required = false) Integer quantity){
        productsService.updateProduct(productId,ownerId,name,description,quantity);
        Map<String,Object> data = productsService.getProductById(productId);
        return Result.success(data,"修改商品信息成功");
    }

    // 删除商品
    @CacheEvict(value = "product",key = "#productId")
    @DeleteMapping("/{productId}")
    public Result<?> deleteProduct(@PathVariable("productId") Integer productId){
        productsService.deleteProduct(productId);
        return Result.success("删除商品成功");
    }

}
