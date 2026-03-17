package com.system.shop.mapper;

import com.system.shop.base.BaseMapper;
import com.system.shop.entity.AfterSale;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface AfterSaleMapper extends BaseMapper<AfterSale> {

    List<AfterSale> findPage(@Param("param") Map<String, Object> querymap);

    AfterSale selectByAfterSaleCode(@Param("afterSaleCode") String afterSaleCode);
}