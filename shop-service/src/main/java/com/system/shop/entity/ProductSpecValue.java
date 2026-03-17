package com.system.shop.entity;

import com.system.shop.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 商品规格value
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class ProductSpecValue extends BaseEntity implements Serializable {
    /**
     * 商品ID
     */
    private Long productId;

    /**
     * 商品规格ID
     */
    private Long productSpecId;

    /**
     * 商品规格类型code
     */
    private String productSpecCode;

    /**
     * 商品规格类型name
     */
    private String productSpecName;

    /**
     * 规格code
     */
    private String code;

    /**
     * 规格名称
     */
    private String name;

    private static final long serialVersionUID = 1L;
}