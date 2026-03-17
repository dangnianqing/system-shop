package com.system.shop.entity;

import com.system.shop.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 活动商品
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class ActivityProduct extends BaseEntity implements Serializable {
    /**
     * 活动ID
     */
    private Long activityId;

    /**
     * 商品ID
     */
    private Long productId;

    private static final long serialVersionUID = 1L;


    private String productName;
    private String productCode;
    private String categoryClassName;
    private Integer quantity;
    private BigDecimal salePrice;
    private Integer upDown;
}