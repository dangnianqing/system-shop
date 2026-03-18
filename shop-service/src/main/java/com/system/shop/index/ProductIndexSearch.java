package com.system.shop.index;

import com.github.pagehelper.PageParam;
import lombok.Data;

@Data
public class ProductIndexSearch extends PageParam {
    private String keyword;
    private Integer maxPrice;
    private Integer minPrice;
}
