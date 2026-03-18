package com.system.shop.bean.cart;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Author ：zhanghaijun
 * @Date ：Created in  2024/5/10 17:08
 * @Description：
 */
@Data
public class CartProductItem implements Serializable {
    private String storeCode;
    private String productCode;
    private String skuCode;
    private Long productId;
    private Long productSkuId;
    private String productName;
    private Integer num;
    private String image;
    private BigDecimal salePrice;
    private String specInfo;
    private Integer quantity;
    private boolean selected = true;
    private static final long serialVersionUID = 1L;


    public CartProductItem(CartSku cartSku) {
        this.productCode = cartSku.getProductCode();
        this.skuCode = cartSku.getSkuCode();
        this.num = cartSku.getNum();
        this.selected=cartSku.isSelected();
        this.storeCode=cartSku.getStoreCode();
    }

    public CartProductItem() {
    }
}
