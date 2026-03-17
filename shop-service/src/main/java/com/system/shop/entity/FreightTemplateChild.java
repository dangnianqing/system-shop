package com.system.shop.entity;

import com.system.shop.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 运费模版
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class FreightTemplateChild extends BaseEntity implements Serializable {
    /**
     * 运费模板ID
     */
    private Long freightTemplateId;

    /**
     * 区域name
     */
    private String regionName;

    /**
     * 区域ID
     */
    private String regionId;

    /**
     * 首重/首件
     */
    private BigDecimal firstNum;

    /**
     * 运费
     */
    private BigDecimal firstPrice;

    /**
     * 续重/续件
     */
    private BigDecimal continuedNum;

    /**
     * 续费
     */
    private BigDecimal continuedPrice;

    private static final long serialVersionUID = 1L;
}