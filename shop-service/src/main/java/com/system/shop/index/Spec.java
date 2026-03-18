package com.system.shop.index;

import com.system.shop.entity.ProductSpec;
import com.system.shop.entity.ProductSpecValue;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Spec {
    private String specId;
    private String title;
    private String specCode;
    private List<SpecValue> specValueList;

    public Spec(ProductSpec productSpec) {
        this.specId = String.valueOf(productSpec.getId());
        this.title = productSpec.getName();
        this.specCode = productSpec.getCode();
        List<SpecValue> specValues = new ArrayList<>();
        for (ProductSpecValue productSpecValue : productSpec.getProductSpecValues()) {
            specValues.add(new SpecValue(productSpecValue));
        }
        this.specValueList = specValues;
    }

    public Spec() {
    }
}
