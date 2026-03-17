package com.system.shop.entity;

import com.system.shop.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 商品sku图片
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class ProductSkuImage extends BaseEntity implements Serializable {
    /**
     * sku_id
     */
    private Long skuId;

    /**
     * 图片地址
     */
    private String imagePath;

    private static final long serialVersionUID = 1L;
}