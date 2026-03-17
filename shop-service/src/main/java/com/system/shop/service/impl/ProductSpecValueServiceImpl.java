package com.system.shop.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.system.shop.base.ServiceImpl;
import com.system.shop.mapper.ProductSpecValueMapper;
import com.system.shop.entity.ProductSpec;
import com.system.shop.entity.ProductSpecValue;
import com.system.shop.service.ProductSpecValueService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ProductSpecValueServiceImpl extends ServiceImpl<ProductSpecValueMapper, ProductSpecValue> implements ProductSpecValueService {


    @Override
    public List<ProductSpecValue> selectBySpecIdAndProductId(Long productSpecId, Long productId) {
        return baseMapper.selectBySpecIdAndProductId(productSpecId, productId);
    }

    @Override
    public boolean updateList(List<ProductSpec> specList, Long productId) {
        baseMapper.deleteByProductId(productId);
        if (CollectionUtil.isNotEmpty(specList)) {
            List<ProductSpecValue> specValueList = new ArrayList<>();
            specList.forEach(spec -> {
                spec.getProductSpecValues().forEach(specValue -> {
                    specValue.setProductId(productId);
                    specValue.setProductSpecId(spec.getId());
                    specValue.setProductSpecCode(spec.getCode());
                    specValue.setProductSpecName(spec.getName());
                    specValueList.add(specValue);
                });
            });
            this.batchInsert(specValueList);
        }
        return true;
    }

    @Override
    public Map<Long, List<ProductSpecValue>> selectProductId(Long productId) {
        List<ProductSpecValue> list = baseMapper.selectByProductId(productId);
        return list.stream().collect(Collectors.groupingBy(ProductSpecValue::getProductSpecId));
    }
}
