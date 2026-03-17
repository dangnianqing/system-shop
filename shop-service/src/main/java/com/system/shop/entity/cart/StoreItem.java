package com.system.shop.entity.cart;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class StoreItem implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String storeCode;    // 门店编码
    private String storeName;    // 门店名称

    private List<ProductItem> productItems;
} 