package com.system.shop.mapper;

import com.system.shop.base.BaseMapper;
import com.system.shop.entity.SysSetting;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SysSettingMapper extends BaseMapper<SysSetting> {

    SysSetting selectBySettingCode(@Param("settingCode") String settingCode);
}