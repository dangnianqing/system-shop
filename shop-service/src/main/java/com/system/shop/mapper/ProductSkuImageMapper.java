package com.system.shop.mapper;

import com.system.shop.base.BaseMapper;
import com.system.shop.entity.ProductSkuImage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ProductSkuImageMapper extends BaseMapper<ProductSkuImage> {

    List<ProductSkuImage> selectBySkuId(@Param("skuId") Long skuId);

    boolean deleteBySkuId(@Param("skuId")Long skuId);
}