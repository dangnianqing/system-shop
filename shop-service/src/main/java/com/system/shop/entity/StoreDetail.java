package com.system.shop.entity;

import com.system.shop.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 店铺信息表
 */
@Data
@EqualsAndHashCode(callSuper=true)
@AllArgsConstructor
@NoArgsConstructor
public class StoreDetail extends BaseEntity implements Serializable {
    /**
    * 门店ID
    */
    private Long storeId;

    /**
    * 门店名称
    */
    private String storeName;

    /**
    * 门店地址ID
    */
    private String storeAddressPathId;

    /**
    * 门店地址名称
    */
    private String storeAddressPathName;

    /**
    * 门店详细地址
    */
    private String storeAddressDetail;

    /**
    * 店铺描述
    */
    private String storeDetail;

    /**
    * 发货人姓名
    */
    private String deliveryName;

    /**
    * 发货人手机
    */
    private String deliveryMobile;

    /**
    * 发货地址ID
    */
    private String deliveryAddressPathId;

    /**
    * 发货地址名称
    */
    private String deliveryAddressPathName;

    /**
    * 发货详细地址
    */
    private String deliveryAddressDetail;

    /**
    * 收货人姓名
    */
    private String afterSaleName;

    /**
    * 收货人手机
    */
    private String afterSaleMobile;

    /**
    * 收货地址ID
    */
    private String afterSaleAddressPathId;

    /**
    * 收货地址名称
    */
    private String afterSaleAddressPathName;

    /**
    * 收货详细地址
    */
    private String afterSaleAddressDetail;

    private static final long serialVersionUID = 1L;
}