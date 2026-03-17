package com.system.shop.entity;

import com.system.shop.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

/**
 * 门店系统菜单表
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class StoreMenu extends BaseEntity implements Serializable {
    /**
     * 父id
     */
    private Long parentId;

    /**
     * 菜单名称
     */
    private String title;

    /**
     * 路由名称
     */
    private String name;

    /**
     * 层级
     */
    private Integer level;

    /**
     * 菜单类型
     */
    private Integer type;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 排序值
     */
    private Integer sort;

    private static final long serialVersionUID = 1L;
    private List<StoreMenu> children;
    private String parentTitle;

}