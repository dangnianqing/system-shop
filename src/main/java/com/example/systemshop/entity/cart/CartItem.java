package com.example.systemshop.entity.cart;

import lombok.Data;

import java.io.Serializable;
import java.util.List;


@Data
public class CartItem implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<StoreItem> storeItems;
}