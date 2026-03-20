package com.system.shop.service;


import com.system.shop.bean.cart.CartItem;
import com.system.shop.bean.cart.CartSku;
import com.system.shop.bean.cart.ProductSkuItem;
import com.system.shop.bean.cart.Settlement;
import com.system.shop.bean.cart.ShoppSettlement;
import com.system.shop.entity.OrderHeader;
import com.system.shop.enumer.CartEnum;

import java.util.List;

/**
 * @Author ：zhanghaijun
 * @Date ：Created in  2024/5/10 17:04
 * @Description：
 */
public interface CartService {
    Boolean add(Long memberId, CartSku cartSku);

    Boolean updateNum(Long memberId, CartSku cartSku);

    Boolean updateProduct(Long memberId, ProductSkuItem productSkuItem);

    Boolean delete(Long memberId, List<ProductSkuItem> productSkuItemList);

    Boolean checked(Long memberId,CartSku cartSku, CartEnum CartEnum);

    Boolean deleteProduct(Long memberId, CartSku cartSku);

    CartItem all(Long memberId);

    Integer count(Long memberId);

    ShoppSettlement settlement(Long memberId, Settlement settlement);

    OrderHeader createOrder(Long memberId, Settlement settlement);


}
