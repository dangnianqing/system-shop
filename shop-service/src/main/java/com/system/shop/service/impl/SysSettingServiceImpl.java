package com.system.shop.service.impl;

import com.system.shop.base.ServiceImpl;
import com.system.shop.mapper.SysSettingMapper;
import com.system.shop.entity.SysSetting;
import com.system.shop.service.SysSettingService;
import org.springframework.stereotype.Service;

@Service
public class SysSettingServiceImpl extends ServiceImpl<SysSettingMapper, SysSetting> implements SysSettingService {


    @Override
    public Boolean saveOrUpdate(SysSetting sysSetting) {
        SysSetting bean = this.selectBySettingCode(sysSetting.getSettingCode());
        if (bean == null) {
            return this.insert(sysSetting);
        } else {
            bean.setSettingValue(sysSetting.getSettingValue());
            return this.updateById(bean);
        }
    }

    @Override
    public SysSetting selectBySettingCode(String settingCode) {
        return baseMapper.selectBySettingCode(settingCode);
    }
}
