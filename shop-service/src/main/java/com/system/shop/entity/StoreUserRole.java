package com.system.shop.entity;

import com.system.shop.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 门店系统用户角色表
 */
@Data
@EqualsAndHashCode(callSuper=true)
public class StoreUserRole extends BaseEntity implements Serializable {
    /**
    * 用户ID
    */
    private Long userId;

    /**
    * 角色ID
    */
    private Long roleId;

    private static final long serialVersionUID = 1L;

    public StoreUserRole(Long userId, Long roleId) {
        this.userId = userId;
        this.roleId = roleId;
    }
}