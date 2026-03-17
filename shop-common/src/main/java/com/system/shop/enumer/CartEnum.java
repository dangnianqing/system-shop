package com.system.shop.enumer;

/**
 * @Author ：zhanghaijun
 * @Date ：Created in  2024/5/10 17:29
 * @Description：
 */
public enum CartEnum {
    PRODUCT, STORE, ALL,CART,BUY;

    public String getPrefix() {
        return "{" + this.name() + "}_";
    }
}
