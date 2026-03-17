package com.system.shop.bean.order;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class AfterSaleInfo implements Serializable {
    /**
     * 订单编号
     */
    private String orderCode;
    /**
     * 售后类型
     */
    private String orderAfterType;

    /**
     * 售后原因Id
     */
    private Long reasonId;

    /**
     * 售后原因
     */
    private String reason;

    /**
     * 售后说明
     */
    private String explain;

    /**
     * 图片
     */
    private String image;

    private List<AfterSaleItemInfo> afterSaleItemInfos;
}
