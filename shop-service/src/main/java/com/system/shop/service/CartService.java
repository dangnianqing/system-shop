package com.system.shop.service;

import com.system.shop.entity.cart.CartItem;
import com.system.shop.entity.cart.ProductItem;

import java.math.BigDecimal;

public interface CartService {
    
    /**
     * 添加商品到购物车
     * @param userId 用户ID
     * @param storeCode 门店编码
     * @param storeName 门店名称
     * @param productItem 商品信息
     */
    void addToCart(Long userId, String storeCode, String storeName, ProductItem productItem);
    
    /**
     * 从购物车中删除商品
     * @param userId 用户ID
     * @param storeCode 门店编码
     * @param productCode 商品编码
     * @param skuCode SKU编码
     */
    void removeFromCart(Long userId, String storeCode, String productCode, String skuCode);
    
    /**
     * 修改购物车中商品数量
     * @param userId 用户ID
     * @param storeCode 门店编码
     * @param productCode 商品编码
     * @param skuCode SKU编码
     * @param quantity 新的数量
     */
    void updateQuantity(Long userId, String storeCode, String productCode, String skuCode, Integer quantity);
    
    /**
     * 获取购物车详情
     * @param userId 用户ID
     * @return 购物车信息
     */
    CartItem getCart(Long userId);
    
    /**
     * 清空购物车
     * @param userId 用户ID
     */
    void clearCart(Long userId);
    
    /**
     * 设置商品选中状态
     * @param userId 用户ID
     * @param storeCode 门店编码
     * @param productCode 商品编码
     * @param skuCode SKU编码
     * @param selected 是否选中
     */
    void setProductSelected(Long userId, String storeCode, String productCode, String skuCode, boolean selected);
    
    /**
     * 设置门店下所有商品选中状态
     * @param userId 用户ID
     * @param storeCode 门店编码
     * @param selected 是否选中
     */
    void setStoreSelected(Long userId, String storeCode, boolean selected);
    
    /**
     * 设置所有商品选中状态
     * @param userId 用户ID
     * @param selected 是否选中
     */
    void setAllSelected(Long userId, boolean selected);
    
    /**
     * 获取选中商品的总价
     * @param userId 用户ID
     * @return 总价
     */
    BigDecimal getSelectedTotalPrice(Long userId);
    
    /**
     * 获取选中商品的总件数
     * @param userId 用户ID
     * @return 总件数
     */
    Integer getSelectedTotalQuantity(Long userId);
} 