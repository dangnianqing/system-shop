package com.system.shop.mapper;

import com.system.shop.base.BaseMapper;
import com.system.shop.entity.FreightTemplate;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface FreightTemplateMapper extends BaseMapper<FreightTemplate> {

    List<FreightTemplate> selectByStoreId(@Param("storeId") Long storeId);

}