package com.system.shop.bean.cart;

import com.system.shop.entity.MemberAddress;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShoppSettlement implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long memberId;
    private Long addressId;
    private MemberAddress memberAddress;
    private List<Long> couponIds;
    private Integer productNum;
    private BigDecimal productPrice;
    private BigDecimal totalPrice = BigDecimal.ZERO;
    private BigDecimal couponPrice = BigDecimal.ZERO;
    private BigDecimal activityPrice = BigDecimal.ZERO;
    private BigDecimal discountPrice = BigDecimal.ZERO;
    private BigDecimal freightPrice = BigDecimal.ZERO;
    private BigDecimal payPrice = BigDecimal.ZERO;
    private List<StoreSettlement> storeSettlements;
    private List<ProductSettlement> notStockProducts = new ArrayList<>();
    private List<ProductSettlement> notDeliveryProducts = new ArrayList<>();

    public ShoppSettlement(Long memberId, Long addressId, List<Long> couponIds, List<StoreSettlement> storeSettlements) {
        this.memberId = memberId;
        this.addressId = addressId;
        this.storeSettlements = storeSettlements;
        this.couponIds = couponIds;
    }
}
