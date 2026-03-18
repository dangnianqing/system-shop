package com.system.shop.bean.cart;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @Author ：zhanghaijun
 * @Date ：Created in  2024/5/10 17:07
 * @Description：
 */
@Data
public class CartItem implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<CartStoreItem> storeItems;
    private Long memberId;
    private boolean checked;

    private Integer totalNum;
    private BigDecimal totalPrice;
    private BigDecimal activityPrice;
    private BigDecimal payPrice;

    public CartItem(Long memberId, List<CartStoreItem> storeItems,BigDecimal totalPrice,Integer totalNum,BigDecimal activityPrice) {
        this.memberId = memberId;
        this.storeItems = storeItems;
        this.totalPrice = totalPrice;
        this.totalNum=totalNum;
        this.activityPrice = activityPrice;
        this.checked= storeItems.stream().allMatch(CartStoreItem::isChecked);
    }

    public CartItem() {
    }
}
