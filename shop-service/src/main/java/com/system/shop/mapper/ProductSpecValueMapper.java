package com.system.shop.mapper;

import com.system.shop.base.BaseMapper;
import com.system.shop.entity.ProductSpecValue;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ProductSpecValueMapper extends BaseMapper<ProductSpecValue> {

    List<ProductSpecValue> selectBySpecIdAndProductId(@Param("productSpecId")Long productSpecId,@Param("productId") Long productId);

    boolean deleteByProductId(@Param("productId")Long productId);


    List<ProductSpecValue> selectByProductId(@Param("productId")Long productId);
}