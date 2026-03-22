package com.system.shop.service;

import com.system.shop.base.IService;
import com.system.shop.entity.Product;

import java.util.List;

public interface ProductService extends IService<Product> {


    Boolean updateUpDown(List<Long> productIds, Integer upDownStatus);

    Product selectProductById(Long id);

    List<Product> selectProductByIds(List<Long> productIds);

    Boolean updateReviewProduct(Long productId, Integer auditStatus);

    Boolean sendProductMsg(List<Long> productIds, Integer status);

    Long saveFirst(Product product);

    Long saveSecond(Product product);

    Long saveThird(Product product);

    Product selectFirst(Long id);

    Product selectSecond(Long id);

    Product selectThird(Long id);

    Boolean updateCancelAudit(List<Long> productIds);

    List<Long> selectProductIds();
}


