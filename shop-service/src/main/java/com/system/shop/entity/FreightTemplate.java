package com.system.shop.entity;

import com.system.shop.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 运费模版
 */
@Data
@EqualsAndHashCode(callSuper=true)
@AllArgsConstructor
@NoArgsConstructor
public class FreightTemplate extends BaseEntity implements Serializable {
    /**
    * 店铺ID
    */
    private Long storeId;

    /**
    * 模板名称
    */
    private String name;

    /**
    * 计价方式 按件、按重量、包邮
    */
    private String pricingMethod;

    private static final long serialVersionUID = 1L;

    private List<FreightTemplateChild> freightTemplateChildList;
}