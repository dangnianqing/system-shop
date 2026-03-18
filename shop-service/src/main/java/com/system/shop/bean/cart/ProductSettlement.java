package com.system.shop.bean.cart;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductSettlement implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long productId;
    private Long productSkuId;
    private String productName;
    private String productCode;
    private String productSkuCode;
    private String image;
    private String specInfo;
    private Integer num;
    private Long freightTemplateId;
    private BigDecimal weight;
    private BigDecimal salePrice;
    private BigDecimal productPrice;
    private BigDecimal promotionPrice;
    private BigDecimal discountPrice;
    private BigDecimal activityPrice;
    private BigDecimal couponPrice;
    private BigDecimal payPrice;

    public ProductSettlement(Long productId, Long productSkuId, Integer num) {
        this.productId = productId;
        this.productSkuId = productSkuId;
        this.num = num;
    }

}
