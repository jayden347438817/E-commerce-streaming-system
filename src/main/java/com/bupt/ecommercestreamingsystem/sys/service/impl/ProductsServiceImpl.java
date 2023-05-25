package com.bupt.ecommercestreamingsystem.sys.service.impl;

import com.bupt.ecommercestreamingsystem.sys.entity.Products;
import com.bupt.ecommercestreamingsystem.sys.mapper.ProductsMapper;
import com.bupt.ecommercestreamingsystem.sys.service.IProductsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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

}
