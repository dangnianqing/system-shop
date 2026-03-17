package com.system.shop.mapper;

import com.system.shop.base.BaseMapper;
import com.system.shop.entity.FreightTemplateChild;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface FreightTemplateChildMapper extends BaseMapper<FreightTemplateChild> {

    List<FreightTemplateChild> selectByFreightTemplateId(@Param("freightTemplateId") Long freightTemplateId);

    boolean deleteByFreightTemplateId(@Param("freightTemplateId") Long freightTemplateId);
}