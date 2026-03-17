package com.system.shop.bean.mq;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ProductPush implements Serializable {
    private List<Long> productIds;
    /**
     * 1下发
     * 0撤回
     */
    private Integer status;

    public ProductPush(List<Long> productIds, Integer status) {
        this.productIds = productIds;
        this.status = status;
    }
}
