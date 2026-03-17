package com.system.shop.mapper;

import com.system.shop.base.BaseMapper;
import com.system.shop.entity.AfterSaleLog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AfterSaleLogMapper extends BaseMapper<AfterSaleLog> {

    List<AfterSaleLog> selectByAfterSaleCode(@Param("afterSaleCode") String afterSaleCode);
}