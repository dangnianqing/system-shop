package com.system.shop.entity;

import com.system.shop.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

/**
 * 门店系统角色表
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class StoreRole extends BaseEntity implements Serializable {
    /**
     * 门店ID
     */
    private Long storeId;

    /**
     * 角色名称
     */
    private String name;

    /**
     * 备注
     */
    private String remark;

    private static final long serialVersionUID = 1L;

    private List<Long> menuIds;
}