package com.system.shop.entity;

import com.system.shop.base.BaseEntity;
import lombok.Data;

import java.io.Serializable;


/**
 * 系统设置
 */
@Data
public class SysSetting extends BaseEntity implements Serializable {
    /**
     * ID
     */
    private String settingCode;

    /**
     * 配置值value
     */
    private String settingValue;

    public SysSetting(String settingCode, String settingValue) {
        this.settingCode = settingCode;
        this.settingValue = settingValue;
    }
    public SysSetting() {
    }

}