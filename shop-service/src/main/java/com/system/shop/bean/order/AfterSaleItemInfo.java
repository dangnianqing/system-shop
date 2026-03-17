package com.system.shop.bean.order;

import lombok.Data;

import java.io.Serializable;

@Data
public class AfterSaleItemInfo implements Serializable {
    /**
     * 订单明细行编号
     */
    private String orderItemCode;
    /**
     * 售后数量
     */
    private Integer afterNum;


}
