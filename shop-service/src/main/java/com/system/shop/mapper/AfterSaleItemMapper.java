package com.system.shop.mapper;

import com.system.shop.base.BaseMapper;
import com.system.shop.entity.AfterSaleItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AfterSaleItemMapper extends BaseMapper<AfterSaleItem> {

    List<AfterSaleItem> selectByAfterSaleCode(@Param("afterSaleCode") String afterSaleCode);
}