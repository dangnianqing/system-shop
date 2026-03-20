package com.system.shop.service;

import com.system.shop.base.IService;
import com.system.shop.entity.SysSetting;

public interface SysSettingService extends IService<SysSetting> {


    Boolean saveOrUpdate(SysSetting sysSetting);

    SysSetting selectBySettingCode(String settingCode);
}

