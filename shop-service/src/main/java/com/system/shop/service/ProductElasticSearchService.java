package com.system.shop.service;

import com.system.shop.index.ProductIndex;
import com.system.shop.index.ProductIndexSearch;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductElasticSearchService {

    Page<ProductIndex> searchPage(ProductIndexSearch search);

    Boolean insertList(List<Long> productIds);

    ProductIndex searchByProductId(String productId);

    Boolean deleteByProductIds(List<Long> productIds);
}
