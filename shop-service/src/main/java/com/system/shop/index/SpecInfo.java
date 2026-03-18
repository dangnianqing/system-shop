package com.system.shop.index;

import com.system.shop.entity.ProductSpecValue;
import lombok.Data;

@Data
public class SpecInfo {
    private String specId;
    private String specTitle;
    private String specCode;
    private String specValueName;
    private String specValueCode;

    public SpecInfo(ProductSpecValue productSpecValue) {
        this.specId = String.valueOf(productSpecValue.getProductSpecId());
        this.specTitle = productSpecValue.getProductSpecName();
        this.specCode = productSpecValue.getProductSpecCode();
        this.specValueName = productSpecValue.getName();
        this.specValueCode = productSpecValue.getCode();
    }

    public SpecInfo() {
    }
}
