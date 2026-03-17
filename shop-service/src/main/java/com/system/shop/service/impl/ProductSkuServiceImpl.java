package com.system.shop.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.system.shop.base.ServiceImpl;
import com.system.shop.mapper.ProductSkuMapper;
import com.system.shop.entity.ProductSku;
import com.system.shop.entity.ProductSpec;
import com.system.shop.entity.ProductSpecValue;
import com.system.shop.service.ProductSkuImageService;
import com.system.shop.service.ProductSkuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class ProductSkuServiceImpl extends ServiceImpl<ProductSkuMapper, ProductSku> implements ProductSkuService {

    @Autowired
    private ProductSkuImageService productSkuImageService;

    @Override
    public List<ProductSku> selectByProductId(Long productId) {
        List<ProductSku> skuList = baseMapper.selectByProductId(productId);
        return skuList;
    }


    @Override
    public List<ProductSku> createSkuList(Long productId, String productCode, String productName, List<ProductSpec> specList) {

        Map<String, ProductSku> initSkuMap = initSkuList(productCode, productName, specList);

        List<ProductSku> skus = new ArrayList<>();
        if (productId == null) {
            initSkuMap.forEach((key, value) -> {
                skus.add(value);
            });
            skus.sort(Comparator.comparing(ProductSku::getProductSkuCode));
            return skus;
        }
        List<ProductSku> skuList = this.selectByProductId(productId);
        if (CollectionUtil.isEmpty(skuList)) {
            initSkuMap.forEach((key, value) -> {
                skus.add(value);
            });
            skus.sort(Comparator.comparing(ProductSku::getProductSkuCode));
            return skus;
        }
        Map<String, ProductSku> skuMap = skuList.stream().collect(Collectors.toMap(ProductSku::getProductSkuCode, Function.identity()));
        for (Map.Entry<String, ProductSku> entry : initSkuMap.entrySet()) {
            String key = entry.getKey();
            ProductSku sku = entry.getValue();
            if (skuMap.containsKey(key)) {
                skuMap.get(key).setSpecInfo(sku.getSpecInfo());
                skus.add(skuMap.get(key));
            } else {
                sku.setProductId(productId);
                skus.add(sku);
            }
        }
        skus.sort(Comparator.comparing(ProductSku::getProductSkuCode));
        return skus;
    }

    @Override
    public boolean updateList(List<ProductSku> skuList, Long productId) {
        List<ProductSku> oldSkuList = this.selectByProductId(productId);
        if (CollectionUtil.isEmpty(oldSkuList)) {
            skuList.forEach(sku -> {
                sku.setProductId(productId);
            });
            return batchInsert(skuList);
        }
        Map<String, ProductSku> newSkuMap = skuList.stream().collect(Collectors.toMap(ProductSku::getProductSkuCode, Function.identity()));
        Map<String, ProductSku> oldSkuMap = oldSkuList.stream().collect(Collectors.toMap(ProductSku::getProductSkuCode, Function.identity()));
        List<ProductSku> updateSkuList = new ArrayList<>();
        List<ProductSku> addSkuList = new ArrayList<>();
        for (Map.Entry<String, ProductSku> entry : newSkuMap.entrySet()) {
            String key = entry.getKey();
            ProductSku sku = entry.getValue();
            if (oldSkuMap.containsKey(key)) {
                sku.setId(oldSkuMap.get(key).getId());
                updateSkuList.add(sku);
            } else {
                addSkuList.add(sku);
            }
            oldSkuMap.remove(key);
        }
        if (CollectionUtil.isNotEmpty(addSkuList)) {
            addSkuList.forEach(sku -> {
                sku.setProductId(productId);
                this.insert(sku);
            });
        }
        if (CollectionUtil.isNotEmpty(updateSkuList)) {
            updateSkuList.forEach(sku -> {
                sku.setProductId(productId);
                this.updateByIdSelective(sku);
            });
        }
        if (CollectionUtil.isNotEmpty(oldSkuMap)) {
            oldSkuMap.values().forEach(sku -> {
                this.deleteById(sku.getId());
            });
        }
        return true;
    }

    @Override
    public boolean updateReduceStock(Long productSkuId, Integer num) {
        return baseMapper.updateReduceStock( productSkuId, num);
    }

    @Override
    public boolean updateIncreaseStock(Long productSkuId, Integer num) {
        return baseMapper.updateIncreaseStock( productSkuId, num);
    }

    private Map<String, ProductSku> initSkuList(String productCode, String productName, List<ProductSpec> specList) {
        specList.forEach(spec -> {
            spec.getProductSpecValues().forEach(specValue -> {
                specValue.setProductSpecCode(spec.getCode());
                specValue.setProductSpecId(spec.getId());
                specValue.setProductSpecName(spec.getName());
            });
        });


        List<List<ProductSpecValue>> specValueCombos = generateSpecValueCombos(specList);
        Map<String, ProductSku> skuList = generateSkus(productCode, productName, specValueCombos);
        return skuList;

    }

    private static List<List<ProductSpecValue>> generateSpecValueCombos(List<ProductSpec> specList) {
        List<List<ProductSpecValue>> specValueCombos = new ArrayList<>();
        generateSpecValueCombosHelper(specList, 0, new ArrayList<>(), specValueCombos);
        return specValueCombos;
    }

    private static void generateSpecValueCombosHelper(List<ProductSpec> specList, int index, List<ProductSpecValue> currentCombo, List<List<ProductSpecValue>> result) {
        if (index == specList.size()) {
            result.add(new ArrayList<>(currentCombo));
            return;
        }

        ProductSpec currentSpec = specList.get(index);
        for (ProductSpecValue value : currentSpec.getProductSpecValues()) {
            currentCombo.add(value);
            generateSpecValueCombosHelper(specList, index + 1, currentCombo, result);
            currentCombo.remove(currentCombo.size() - 1);
        }
    }

    private static Map<String, ProductSku> generateSkus(String productCode, String productName, List<List<ProductSpecValue>> specValueCombos) {
        Map<String, ProductSku> sku = new HashMap<>();
        for (List<ProductSpecValue> combo : specValueCombos) {
            StringBuilder skuCode = new StringBuilder();
            StringBuilder skuName = new StringBuilder();
            skuCode.append(productCode);
            skuName.append(productName);
            for (ProductSpecValue value : combo) {
                skuCode.append(value.getCode());
                skuName.append(" ");
                skuName.append(value.getName());
            }
            sku.put(skuCode.toString(), new ProductSku(skuCode.toString(), skuName.toString(), combo));
        }
        return sku;
    }
}

