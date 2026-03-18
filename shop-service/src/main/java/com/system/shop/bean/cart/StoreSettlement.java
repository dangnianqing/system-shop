package com.system.shop.bean.cart;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StoreSettlement implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long storeId;
    private String storeName;
    private Integer productNum;
    private BigDecimal productPrice;
    private BigDecimal totalPrice = BigDecimal.ZERO;
    private BigDecimal couponPrice = BigDecimal.ZERO;
    private BigDecimal activityPrice = BigDecimal.ZERO;
    private BigDecimal discountPrice = BigDecimal.ZERO;
    private BigDecimal freightPrice = BigDecimal.ZERO;
    private BigDecimal payPrice = BigDecimal.ZERO;
    private String remark;
    private List<ProductSettlement> productSettlements;


    public StoreSettlement(Long storeId, List<ProductSettlement> productSettlements) {
        this.storeId = storeId;
        this.productSettlements = productSettlements;
    }

}
