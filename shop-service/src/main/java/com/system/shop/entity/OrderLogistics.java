package com.system.shop.entity;

import cn.hutool.json.JSONArray;
import com.system.shop.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 订单发货物流单号
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class OrderLogistics extends BaseEntity implements Serializable {
    /**
     * 订单号
     */
    private String orderCode;

    /**
     * 物流公司CODE
     */
    private Long logisticsCompanyId;

    /**
     * 物流公司code
     */
    private String logisticsCompanyCode;

    /**
     * 物流公司名称
     */
    private String logisticsCompanyName;

    /**
     * 发货单号
     */
    private String logisticsNo;

    private static final long serialVersionUID = 1L;


    private JSONArray nodes;
    private Integer state;
}