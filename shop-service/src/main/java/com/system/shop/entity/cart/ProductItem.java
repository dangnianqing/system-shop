package com.system.shop.entity.cart;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class ProductItem implements Serializable {
    private static final long serialVersionUID = 1L;

    private String productCode;
    private String skuCode;
    private String productName;
    private BigDecimal price;
    private Integer quantity;
    private boolean selected = false;  // 默认未选中
}
