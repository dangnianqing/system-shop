package com.system.shop.entity;

import com.system.shop.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 商品属性（尺码）
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class ProductSize extends BaseEntity implements Serializable {
    /**
     * 尺码CODE
     */
    private String code;

    /**
     * 尺码名称
     */
    private String name;

    private static final long serialVersionUID = 1L;
}