package com.system.shop.entity;

import com.system.shop.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 管理系统菜单表
 */
@Data
@EqualsAndHashCode(callSuper=true)
public class SysMenu extends BaseEntity implements Serializable {
    /**
    * 菜单标题
    */
    private String name;

    /**
    * 层级
    */
    private Integer level;

    /**
    * 父id
    */
    private Long parentId;
    private String parentName;

    /**
    * 排序值
    */
    private Integer sortOrder;

    private List<SysMenu> children = new ArrayList<>();
    private static final long serialVersionUID = 1L;
}