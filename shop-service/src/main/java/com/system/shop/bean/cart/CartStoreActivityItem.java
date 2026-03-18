package com.system.shop.bean.cart;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @Author ：zhanghaijun
 * @Date ：Created in  2024/5/10 17:08
 * @Description：
 */
@Data
public class CartStoreActivityItem implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<CartProductItem> productItems;

    private String activityName;
    private String description;
    private String activityType;
    private Integer isNeedAddOnShop;
    private BigDecimal discountPrice;


}
