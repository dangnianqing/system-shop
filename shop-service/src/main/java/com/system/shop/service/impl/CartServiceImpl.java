package com.system.shop.service.impl;

import com.system.shop.entity.cart.CartItem;
import com.system.shop.entity.cart.ProductItem;
import com.system.shop.entity.cart.StoreItem;
import com.system.shop.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl implements CartService {

    private static final String CART_KEY_PREFIX = "cart:";

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    private String getCartKey(Long userId) {
        return CART_KEY_PREFIX + userId;
    }

    @Override
    public void addToCart(Long userId, String storeCode, String storeName, ProductItem productItem) {
        String cartKey = getCartKey(userId);
        CartItem cartItem = Optional.ofNullable(getCart(userId))
                .orElseGet(() -> {
                    CartItem newCart = new CartItem();
                    newCart.setStoreItems(new ArrayList<>());
                    return newCart;
                });

        // 查找或创建门店
        StoreItem storeItem = cartItem.getStoreItems().stream()
                .filter(store -> store.getStoreCode().equals(storeCode))
                .findFirst()
                .orElseGet(() -> {
                    StoreItem newStore = new StoreItem();
                    newStore.setStoreCode(storeCode);
                    newStore.setStoreName(storeName);
                    newStore.setProductItems(new ArrayList<>());
                    cartItem.getStoreItems().add(newStore);
                    return newStore;
                });

        // 查找商品是否已存在
        Optional<ProductItem> existingProduct = storeItem.getProductItems().stream()
                .filter(p -> p.getProductCode().equals(productItem.getProductCode()) 
                        && p.getSkuCode().equals(productItem.getSkuCode()))
                .findFirst();

        if (existingProduct.isPresent()) {
            // 更新数量
            ProductItem product = existingProduct.get();
            product.setQuantity(product.getQuantity() + productItem.getQuantity());
        } else {
            // 添加新商品
            storeItem.getProductItems().add(productItem);
        }

        redisTemplate.opsForValue().set(cartKey, cartItem);
    }

    @Override
    public void removeFromCart(Long userId, String storeCode, String productCode, String skuCode) {
        String cartKey = getCartKey(userId);
        CartItem cartItem = getCart(userId);
        
        if (cartItem != null) {
            cartItem.getStoreItems().stream()
                    .filter(store -> store.getStoreCode().equals(storeCode))
                    .findFirst()
                    .ifPresent(store -> {
                        store.setProductItems(
                            store.getProductItems().stream()
                                .filter(product -> !(product.getProductCode().equals(productCode) 
                                    && product.getSkuCode().equals(skuCode)))
                                .collect(Collectors.toList())
                        );
                        
                        // 如果门店下没有商品了，移除门店
                        if (store.getProductItems().isEmpty()) {
                            cartItem.getStoreItems().remove(store);
                        }
                    });

            if (cartItem.getStoreItems().isEmpty()) {
                redisTemplate.delete(cartKey);
            } else {
                redisTemplate.opsForValue().set(cartKey, cartItem);
            }
        }
    }

    @Override
    public void updateQuantity(Long userId, String storeCode, String productCode, String skuCode, Integer quantity) {
        String cartKey = getCartKey(userId);
        CartItem cartItem = getCart(userId);
        
        if (cartItem != null) {
            cartItem.getStoreItems().stream()
                    .filter(store -> store.getStoreCode().equals(storeCode))
                    .findFirst()
                    .ifPresent(store -> 
                        store.getProductItems().stream()
                            .filter(product -> product.getProductCode().equals(productCode) 
                                && product.getSkuCode().equals(skuCode))
                            .findFirst()
                            .ifPresent(product -> product.setQuantity(quantity))
                    );

            redisTemplate.opsForValue().set(cartKey, cartItem);
        }
    }

    @Override
    public CartItem getCart(Long userId) {
        String cartKey = getCartKey(userId);
        return (CartItem) redisTemplate.opsForValue().get(cartKey);
    }

    @Override
    public void clearCart(Long userId) {
        String cartKey = getCartKey(userId);
        redisTemplate.delete(cartKey);
    }

    @Override
    public void setProductSelected(Long userId, String storeCode, String productCode, String skuCode, boolean selected) {
        String cartKey = getCartKey(userId);
        CartItem cartItem = getCart(userId);
        
        if (cartItem != null) {
            cartItem.getStoreItems().stream()
                    .filter(store -> store.getStoreCode().equals(storeCode))
                    .findFirst()
                    .ifPresent(store -> 
                        store.getProductItems().stream()
                            .filter(product -> product.getProductCode().equals(productCode) 
                                && product.getSkuCode().equals(skuCode))
                            .findFirst()
                            .ifPresent(product -> product.setSelected(selected))
                    );

            redisTemplate.opsForValue().set(cartKey, cartItem);
        }
    }

    @Override
    public void setStoreSelected(Long userId, String storeCode, boolean selected) {
        String cartKey = getCartKey(userId);
        CartItem cartItem = getCart(userId);
        
        if (cartItem != null) {
            cartItem.getStoreItems().stream()
                    .filter(store -> store.getStoreCode().equals(storeCode))
                    .findFirst()
                    .ifPresent(store -> 
                        store.getProductItems().forEach(product -> product.setSelected(selected))
                    );

            redisTemplate.opsForValue().set(cartKey, cartItem);
        }
    }

    @Override
    public void setAllSelected(Long userId, boolean selected) {
        String cartKey = getCartKey(userId);
        CartItem cartItem = getCart(userId);
        
        if (cartItem != null) {
            cartItem.getStoreItems().forEach(store -> 
                store.getProductItems().forEach(product -> product.setSelected(selected))
            );

            redisTemplate.opsForValue().set(cartKey, cartItem);
        }
    }

    @Override
    public BigDecimal getSelectedTotalPrice(Long userId) {
        CartItem cartItem = getCart(userId);
        if (cartItem == null) {
            return BigDecimal.ZERO;
        }

        return cartItem.getStoreItems().stream()
                .flatMap(store -> store.getProductItems().stream())
                .filter(ProductItem::isSelected)
                .map(product -> product.getPrice().multiply(new BigDecimal(product.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public Integer getSelectedTotalQuantity(Long userId) {
        CartItem cartItem = getCart(userId);
        if (cartItem == null) {
            return 0;
        }

        return cartItem.getStoreItems().stream()
                .flatMap(store -> store.getProductItems().stream())
                .filter(ProductItem::isSelected)
                .mapToInt(ProductItem::getQuantity)
                .sum();
    }
} 