package com.system.shop.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.system.shop.entity.Product;
import com.system.shop.entity.ProductImage;
import com.system.shop.entity.ProductSku;
import com.system.shop.entity.ProductSpec;
import com.system.shop.index.ProductIndex;
import com.system.shop.index.ProductIndexSearch;
import com.system.shop.index.Sku;
import com.system.shop.repository.ProductIndexRepository;
import com.system.shop.service.ProductElasticSearchService;
import com.system.shop.service.ProductImageService;
import com.system.shop.service.ProductService;
import com.system.shop.service.ProductSkuImageService;
import com.system.shop.service.ProductSkuService;
import com.system.shop.service.ProductSpecService;
import com.system.shop.service.ProductSpecValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Repository
public class ProductElasticSearchServiceImpl implements ProductElasticSearchService {
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductSkuService productSkuService;
    @Autowired
    private ProductImageService productImageService;
    @Autowired
    private ProductSkuImageService productSkuImageService;
    @Autowired
    private ProductSpecService productSpecService;
    @Autowired
    private ProductSpecValueService productSpecValueService;
    @Autowired
    private ProductIndexRepository productIndexRepository;


    @Override
    public Page<ProductIndex> searchPage(ProductIndexSearch search) {
        //Pageable pageable=new PageRequest(search.getPageNum(), search.getPageSize(), null);
        // Order order = new Order(Sort.Direction.valueOf(search.getOrder().toUpperCase()), search.getSort());
        Pageable pageable = PageRequest.of(search.getPageNum() - 1, search.getPageSize());
        return productIndexRepository.findAll(pageable);
    }

    @Override
    public Boolean insertList(List<Long> productIds) {
        List<ProductIndex> indexList = this.init(productIds);
        productIndexRepository.saveAll(indexList);
        return true;
    }

    @Override
    public ProductIndex searchByProductId(String productId) {
        Optional<ProductIndex> productIndex = productIndexRepository.findById(productId);
        Product product = productService.selectById(Long.valueOf(productId));
        productIndex.get().setQuantity(product.getQuantity());
        productIndex.get().setBuyCount(product.getBuyCount());
        List<ProductSku> productSkus = productSkuService.selectByProductId(Long.valueOf(productId));
        Map<Long, ProductSku> skuMap = productSkus.stream().collect(Collectors.toMap(ProductSku::getId, Function.identity()));
        int quantity = 0;
        for (Sku sku : productIndex.get().getSkuList()) {
            ProductSku productSku = skuMap.get(Long.valueOf(sku.getSkuId()));
            if (productSku != null) {
                sku.setQuantity(productSku.getQuantity());
                quantity = quantity + productSku.getQuantity();
            }
        }
        productIndex.get().setQuantity(quantity);
        return productIndex.orElse(null);
    }


    @Override
    public Boolean deleteByProductIds(List<Long> productIds) {
        productIds.forEach(productId -> productIndexRepository.deleteById(String.valueOf(productId)));
        return true;
    }


    private List<ProductIndex> init(List<Long> productIds) {
        List<ProductIndex> indexList = new ArrayList<>();
        productIds.forEach(productId -> {
            List<ProductSpec> specList = new ArrayList<>();
            Product product = productService.selectById(productId);
            List<ProductImage> images = productImageService.selectByProductId(productId);
            if (CollectionUtil.isNotEmpty(images)) {
                product.setImagePath(images.stream().map(ProductImage::getImagePath).collect(Collectors.toList()));
            }
            for (String specId : product.getSpecId().split(",")) {
                ProductSpec productSpec = productSpecService.selectById(Long.valueOf(specId));
                productSpec.setProductSpecValues(productSpecValueService.selectBySpecIdAndProductId(productSpec.getId(), productId));
                specList.add(productSpec);
            }
            product.setSpecList(specList);
            List<ProductSku> skuList = productSkuService.selectByProductId(productId);
            ProductIndex index = new ProductIndex(product, skuList);
            indexList.add(index);
        });
        return indexList;
    }


}
