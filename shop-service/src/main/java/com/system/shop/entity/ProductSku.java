package com.system.shop.entity;

import com.alibaba.fastjson.JSON;
import com.system.shop.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 商品sku表
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class ProductSku extends BaseEntity implements Serializable {
    /**
     * product_id
     */
    private Long productId;

    /**
     * 商品名称
     */
    private String productSkuName;

    /**
     * 商品名称
     */
    private String productSkuCode;

    /**
     * sku规格信息
     */
    private String specInfo;

    private String skuImage;

    /**
     * 购买数量
     */
    private Integer buyCount;

    /**
     * 库存
     */
    private Integer quantity;

    /**
     * 重量
     */
    private BigDecimal weight;

    private static final long serialVersionUID = 1L;

    public ProductSku(String skuCode, String skuName, List<ProductSpecValue> specInfo) {
        this.productSkuCode = skuCode;
        this.productSkuName = skuName;
        this.specInfo = JSON.toJSONString(specInfo);
    }
}