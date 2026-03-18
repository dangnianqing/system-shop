package com.system.shop.bean.cart;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Author ：zhanghaijun
 * @Date ：Created in  2024/5/10 17:08
 * @Description：
 */
@Data
public class CartStoreItem implements Serializable {
    private static final long serialVersionUID = 1L;
    private String storeName;
    private String storeCode;
    private boolean checked;
    private List<CartStoreActivityItem> activityItems;
    private List<CartProductItem> productItems;
    private List<CartProductItem> shortageProductItems;
}
