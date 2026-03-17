package com.system.shop.mapper;

import com.system.shop.base.BaseMapper;
import com.system.shop.entity.ProductImage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ProductImageMapper extends BaseMapper<ProductImage> {

    List<ProductImage> selectByProductId(@Param("productId") Long productId);

    boolean deleteByProductId(@Param("productId")Long productId);
}