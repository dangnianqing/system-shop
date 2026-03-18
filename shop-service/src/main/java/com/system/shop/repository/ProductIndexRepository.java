package com.system.shop.repository;

import com.system.shop.index.ProductIndex;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ProductIndexRepository extends ElasticsearchRepository<ProductIndex, String> {


    @Query("{\"bool\": {\"must\": [{\"term\": {\"productId\": \"?0\"}}, {\"term\": {\"storeId\": \"?1\"}}]}}")
    ProductIndex findByProductIdAndStoreId(String productId, String storeId);
}
