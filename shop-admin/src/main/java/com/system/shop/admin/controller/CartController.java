package com.system.shop.admin.controller;

import com.system.shop.admin.common.Result;
import com.system.shop.service.entity.cart.CartItem;
import com.system.shop.service.entity.cart.ProductItem;
import com.system.shop.admin.security.UserPrincipal;
import com.system.shop.service.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @PostMapping("/add")
    public Result<Void> addToCart(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @RequestParam String storeCode,
            @RequestParam String storeName,
            @RequestBody ProductItem productItem) {
        cartService.addToCart(userPrincipal.getId(), storeCode, storeName, productItem);
        return Result.success();
    }

    @DeleteMapping("/remove")
    public Result<Void> removeFromCart(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @RequestParam String storeCode,
            @RequestParam String productCode,
            @RequestParam String skuCode) {
        cartService.removeFromCart(userPrincipal.getId(), storeCode, productCode, skuCode);
        return Result.success();
    }

    @PutMapping("/quantity")
    public Result<Void> updateQuantity(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @RequestParam String storeCode,
            @RequestParam String productCode,
            @RequestParam String skuCode,
            @RequestParam Integer quantity) {
        cartService.updateQuantity(userPrincipal.getId(), storeCode, productCode, skuCode, quantity);
        return Result.success();
    }

    @GetMapping
    public Result<CartItem> getCart(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        CartItem cart = cartService.getCart(userPrincipal.getId());
        return Result.success(cart);
    }

    @DeleteMapping("/clear")
    public Result<Void> clearCart(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        cartService.clearCart(userPrincipal.getId());
        return Result.success();
    }

    @PutMapping("/product/selected")
    public Result<Void> setProductSelected(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @RequestParam String storeCode,
            @RequestParam String productCode,
            @RequestParam String skuCode,
            @RequestParam boolean selected) {
        cartService.setProductSelected(userPrincipal.getId(), storeCode, productCode, skuCode, selected);
        return Result.success();
    }

    @PutMapping("/store/selected")
    public Result<Void> setStoreSelected(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @RequestParam String storeCode,
            @RequestParam boolean selected) {
        cartService.setStoreSelected(userPrincipal.getId(), storeCode, selected);
        return Result.success();
    }

    @PutMapping("/all/selected")
    public Result<Void> setAllSelected(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @RequestParam boolean selected) {
        cartService.setAllSelected(userPrincipal.getId(), selected);
        return Result.success();
    }

    @GetMapping("/selected/total-price")
    public Result<BigDecimal> getSelectedTotalPrice(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        BigDecimal totalPrice = cartService.getSelectedTotalPrice(userPrincipal.getId());
        return Result.success(totalPrice);
    }

    @GetMapping("/selected/total-quantity")
    public Result<Integer> getSelectedTotalQuantity(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        Integer totalQuantity = cartService.getSelectedTotalQuantity(userPrincipal.getId());
        return Result.success(totalQuantity);
    }
} 