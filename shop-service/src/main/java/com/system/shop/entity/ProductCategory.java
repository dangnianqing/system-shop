package com.system.shop.entity;

import com.system.shop.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 商品属性（sap分类）
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class ProductCategory extends BaseEntity implements Serializable {
    /**
     * 分类名称
     */
    private String name;

    /**
     * 父ID
     */
    private Long parentId;

    /**
     * 层级
     */
    private Integer level;

    /**
     * 分类图标
     */
    private String image;

    /**
     * 排序值
     */
    private Integer sortOrder;

    private List<ProductCategory> children;

    private static final long serialVersionUID = 1L;
}