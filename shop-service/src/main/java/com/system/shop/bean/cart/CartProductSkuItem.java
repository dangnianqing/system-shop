package com.system.shop.bean.cart;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author ：zhanghaijun
 * @Date ：Created in  2024/5/10 17:08
 * @Description：
 */
@Data
public class CartProductSkuItem implements Serializable {

    private Long id;
    private Long productSkuId;
    private Long productId;
    private Long storeId;
    private Integer num;
    private boolean checked;
    private static final long serialVersionUID = 1L;

}
