package com.system.shop.index;

import cn.hutool.json.JSONUtil;
import com.system.shop.entity.ProductSku;
import com.system.shop.entity.ProductSpecValue;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class Sku implements Serializable {
    private String skuId;
    private String skuCode;
    private String skuName;
    /**
     * 库存
     */
    private Integer quantity;
    private String skuImage;

    private List<SpecInfo> specInfo;

    public Sku(ProductSku productSku) {
        this.skuId = String.valueOf(productSku.getId());
        this.skuCode = productSku.getProductSkuCode();
        this.skuName = productSku.getProductSkuName();
        this.skuImage = productSku.getSkuImage();
        this.quantity = productSku.getQuantity();
        List<SpecInfo> specInfos = new ArrayList<>();
        List<ProductSpecValue> list = JSONUtil.toList(productSku.getSpecInfo(), ProductSpecValue.class);
        list.forEach(productSpecValue -> {
            specInfos.add(new SpecInfo(productSpecValue));
        });
        this.specInfo = specInfos;
    }

    public Sku() {
    }
}
