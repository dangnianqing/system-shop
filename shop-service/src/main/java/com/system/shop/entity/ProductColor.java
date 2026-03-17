package com.system.shop.entity;

import com.system.shop.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 商品属性（颜色）
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class ProductColor extends BaseEntity implements Serializable {
    /**
     * 颜色CODE
     */
    private String code;

    /**
     * 颜色名称
     */
    private String name;

    private static final long serialVersionUID = 1L;
}