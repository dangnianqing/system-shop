package com.system.shop.index;

import com.system.shop.entity.ProductSpecValue;
import lombok.Data;

@Data
public class SpecValue {
    private String specValueName;
    private String specValueCode;

    public SpecValue(ProductSpecValue productSpecValue) {
        this.specValueCode = productSpecValue.getCode();
        this.specValueName = productSpecValue.getName();
    }

    public SpecValue() {
    }
}
