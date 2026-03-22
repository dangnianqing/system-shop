package com.system.shop.mapper;

import com.system.shop.base.BaseMapper;
import com.system.shop.entity.Product;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ProductMapper extends BaseMapper<Product> {


    List<Product> selectProductByIds(@Param("productIds") List<Long> productIds);

    List<Long> selectProductIds();
}