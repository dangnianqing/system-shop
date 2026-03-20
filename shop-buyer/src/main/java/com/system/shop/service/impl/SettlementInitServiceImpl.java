package com.system.shop.service.impl;

import com.system.shop.bean.cart.ProductSettlement;
import com.system.shop.bean.cart.ShoppSettlement;
import com.system.shop.bean.cart.StoreSettlement;
import com.system.shop.enumer.SettlementType;
import com.system.shop.entity.Product;
import com.system.shop.entity.ProductSku;
import com.system.shop.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Slf4j
@Service
public class SettlementInitServiceImpl implements SettlementService {
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductSkuService productSkuService;
    @Autowired
    private StoreService storeService;

    @Override
    public SettlementType step() {
        return SettlementType.INIT;
    }

    @Override
    public void compute(ShoppSettlement shoppSettlement) {
        for (StoreSettlement storeSettlement : shoppSettlement.getStoreSettlements()) {
            storeSettlement.setStoreName(storeService.selectById(storeSettlement.getStoreId()).getStoreName());
            for (ProductSettlement productSettlement : storeSettlement.getProductSettlements()) {
                ProductSku sku = productSkuService.selectById(productSettlement.getProductSkuId());
                Product product = productService.selectById(sku.getProductId());
                productSettlement.setProductCode(product.getProductCode());
                productSettlement.setProductName(product.getProductName());
                productSettlement.setProductSkuCode(sku.getProductSkuCode());
                productSettlement.setSalePrice(product.getSalePrice());
                productSettlement.setSpecInfo(sku.getSpecInfo());
                productSettlement.setImage(product.getHeadImage());
                productSettlement.setFreightTemplateId(product.getFreightTemplateId());
                productSettlement.setWeight(product.getWeight());
                productSettlement.setPayPrice(productSettlement.getSalePrice().multiply(new BigDecimal(productSettlement.getNum())));
                productSettlement.setPromotionPrice(productSettlement.getSalePrice().multiply(new BigDecimal(productSettlement.getNum())));
                productSettlement.setProductPrice(productSettlement.getSalePrice().multiply(new BigDecimal(productSettlement.getNum())));
                storeSettlement.setProductPrice(storeSettlement.getProductPrice() == null ? productSettlement.getProductPrice() : storeSettlement.getProductPrice().add(productSettlement.getProductPrice()));
                storeSettlement.setProductNum(storeSettlement.getProductNum() == null ? productSettlement.getNum() : storeSettlement.getProductNum() + productSettlement.getNum());
                storeSettlement.setTotalPrice(storeSettlement.getTotalPrice() == null ? productSettlement.getProductPrice() : storeSettlement.getTotalPrice().add(productSettlement.getProductPrice()));
            }
            shoppSettlement.setProductNum(shoppSettlement.getProductNum() == null ? storeSettlement.getProductNum() : shoppSettlement.getProductNum() + storeSettlement.getProductNum());
            shoppSettlement.setProductPrice(shoppSettlement.getProductPrice() == null ? storeSettlement.getProductPrice() : shoppSettlement.getProductPrice().add(storeSettlement.getProductPrice()));
            shoppSettlement.setTotalPrice(shoppSettlement.getTotalPrice() == null ? storeSettlement.getProductPrice() : shoppSettlement.getTotalPrice().add(storeSettlement.getProductPrice()));
        }
    }
}
