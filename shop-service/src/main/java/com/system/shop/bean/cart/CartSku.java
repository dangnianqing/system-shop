package com.system.shop.bean.cart;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author ：zhanghaijun
 * @Date ：Created in  2024/5/10 17:08
 * @Description：
 */
@Data
public class CartSku implements Serializable {

    private String skuCode;
    private String productCode;
    private String storeCode;
    private Integer num;
    private boolean selected;
    private static final long serialVersionUID = 1L;

}
