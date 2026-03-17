package com.system.shop.entity;

import com.system.shop.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper=true)
@AllArgsConstructor
@NoArgsConstructor
public class ProductBrand extends BaseEntity implements Serializable {
    /**
    * 品牌名称
    */
    private String name;

    /**
    * 品牌图标
    */
    private String logo;

    private static final long serialVersionUID = 1L;
}