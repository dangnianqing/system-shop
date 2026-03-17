package com.system.shop.mapper;

import com.system.shop.base.BaseMapper;
import com.system.shop.entity.ProductSku;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ProductSkuMapper extends BaseMapper<ProductSku> {

    List<ProductSku> selectByProductId(@Param("productId") Long productId);

    boolean updateReduceStock(@Param("productSkuId") Long productSkuId, @Param("num") Integer num);

    boolean updateIncreaseStock(@Param("productSkuId") Long productSkuId, @Param("num") Integer num);
}