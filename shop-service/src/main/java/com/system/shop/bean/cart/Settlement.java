package com.system.shop.bean.cart;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class Settlement implements Serializable {

    private List<Long> couponIds;
    private Long addressId;
    private List<SettlementProduct> products;



    @Data
    public static class SettlementProduct implements Serializable {
        private Long productSkuId;
        private Long productId;
        private Long storeId;
        private Integer num;
    }
}

