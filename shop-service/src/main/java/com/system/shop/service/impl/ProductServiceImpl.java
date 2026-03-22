package com.system.shop.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.util.StringUtil;
import com.system.shop.base.ServiceImpl;
import com.system.shop.bean.mq.ProductPush;
import com.system.shop.common.ResultCode;
import com.system.shop.config.RabbitConfig;
import com.system.shop.exception.BusinessException;
import com.system.shop.utils.SnowFlake;
import com.system.shop.mapper.ProductMapper;
import com.system.shop.entity.*;
import com.system.shop.service.*;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements ProductService {
    @Autowired
    private ProductImageService productImageService;
    @Autowired
    private ProductSkuService productSkuService;
    @Autowired
    private ProductSpecValueService productSpecValueService;
    @Autowired
    private ProductSpecService productSpecService;
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private StoreService storeService;

    @Override
    public Boolean updateUpDown(List<Long> productIds, Integer upDownStatus) {
        if (CollectionUtil.isEmpty(productIds)) {
            return true;
        }
        if (upDownStatus == 1) {
            //上架
            List<Product> list = this.selectProductByIds(productIds);
            boolean isAll = list.stream().allMatch(product -> product.getAuditStatus() == 1);
            if (!isAll) {
                throw new BusinessException(ResultCode.PRODUCT_UNAUDITED);
            }
        }
        List<Product> list = new ArrayList<>();
        for (Long id : productIds) {
            Product product = new Product(id, upDownStatus);
            list.add(product);
        }
        this.sendProductMsg(productIds, upDownStatus);
        return this.updateBatchSelective(list);
    }

    @Override
    public Product selectProductById(Long id) {
        Product product = this.selectById(id);
        product.setImagePath(productImageService.selectByProductId(product.getId()).stream().map(ProductImage::getImagePath).collect(Collectors.toList()));
        //查询sku
        List<ProductSku> skuList = productSkuService.selectByProductId(product.getId());
        product.setSkuList(skuList);
        product.setCategoryClassIds(Arrays.asList(product.getCategoryClassOne(), product.getCategoryClassTwo(), product.getCategoryClassThree(), product.getCategoryClassFour()));
        List<ProductSpec> specList = new ArrayList<>();
        for (String specId : product.getSpecId().split(",")) {
            ProductSpec productSpec = productSpecService.selectById(Long.valueOf(specId));
            productSpec.setProductSpecValues(productSpecValueService.selectProductId(product.getId()).get(productSpec.getId()));
            specList.add(productSpec);
        }
        product.setSpecList(specList);
        return product;
    }


    @Override
    public List<Product> selectProductByIds(List<Long> productIds) {
        return baseMapper.selectProductByIds(productIds);
    }

    @Override
    public Boolean updateReviewProduct(Long productId, Integer auditStatus) {
        Product product = this.selectById(productId);
        if (product == null) {
            throw new BusinessException(ResultCode.PRODUCT_NOT_EXIST);

        }
        Product updateProduct = new Product();
        updateProduct.setId(productId);
        updateProduct.setAuditStatus(auditStatus);
        return this.updateByIdSelective(updateProduct);
    }


    @Override
    public Boolean sendProductMsg(List<Long> productIds, Integer status) {
        ProductPush productPush = new ProductPush(productIds, status);
        CorrelationData correlationData = new CorrelationData(SnowFlake.getIdString());
        Message message = MessageBuilder
                .withBody(JSON.toJSONString(productPush).getBytes(StandardCharsets.UTF_8))
                .setContentType(MessageProperties.CONTENT_TYPE_TEXT_PLAIN)
                .setDeliveryMode(MessageDeliveryMode.PERSISTENT)
                .build();
        // 3. 发送消息到 RabbitMQ 服务器，需要指定交换机、路由键、消息载体以及消息ID
        rabbitTemplate.convertAndSend(RabbitConfig.DIRECT_EXCHANGE, RabbitConfig.product_push_queue_key, message, correlationData);
        return true;
    }

    @Override
    @Transactional
    public Long saveFirst(Product product) {
        if (product.getId() == null) {
            Store store = storeService.selectById(product.getStoreId());
            product.setStoreId(store.getId());
            product.setStoreName(store.getStoreName());
            this.insertSelective(product);
        } else {
            this.updateByIdSelective(product);
        }
        productImageService.updateList(product.getImagePath(), product.getId());
        return product.getId();
    }

    @Override
    @Transactional
    public Long saveSecond(Product product) {
        Product bean = this.selectById(product.getId());
        if (bean == null) {
            throw new BusinessException(ResultCode.PRODUCT_NOT_EXIST);
        }
        productSpecValueService.updateList(product.getSpecList(), product.getId());
        productSkuService.updateList(product.getSkuList(), product.getId());
        return bean.getId();
    }

    @Override
    @Transactional
    public Long saveThird(Product product) {
        Product bean = this.selectById(product.getId());
        if (bean == null) {
            throw new BusinessException(ResultCode.PRODUCT_NOT_EXIST);
        }
        Product updateProduct = new Product();
        updateProduct.setId(product.getId());
        updateProduct.setAuditStatus(product.getAuditStatus());
        updateProduct.setProductInfo(product.getProductInfo());
        this.updateByIdSelective(updateProduct);
        return updateProduct.getId();
    }

    @Override
    public Product selectFirst(Long id) {
        Product product = this.selectById(id);
        product.setProductInfo(null);
        product.setImagePath(productImageService.selectByProductId(product.getId()).stream().map(ProductImage::getImagePath).collect(Collectors.toList()));
        product.setCategoryClassIds(Arrays.asList(product.getCategoryClassOne(), product.getCategoryClassTwo(), product.getCategoryClassThree(), product.getCategoryClassFour()));
        return product;
    }

    @Override
    public Product selectSecond(Long id) {
        Product product = this.selectById(id);
        Product bean = new Product();
        bean.setProductCode(product.getProductCode());
        bean.setProductName(product.getProductName());
        bean.setId(product.getId());
        bean.setSpecId(product.getSpecId());
        //查询sku
        List<ProductSku> skuList = productSkuService.selectByProductId(product.getId());
        bean.setSkuList(skuList);
        if (StringUtil.isNotEmpty(product.getSpecId())) {
            String[] specIds = product.getSpecId().split(",");
            List<ProductSpec> specList = new ArrayList<>();
            for (String specId : specIds) {
                ProductSpec productSpec = productSpecService.selectById(Long.valueOf(specId));
                productSpec.setProductSpecValues(productSpecValueService.selectProductId(product.getId()).get(productSpec.getId()));
                specList.add(productSpec);
            }
            bean.setSpecList(specList);
        }
        return bean;
    }

    @Override
    public Product selectThird(Long id) {
        Product product = this.selectById(id);
        Product bean = new Product();
        bean.setId(product.getId());
        bean.setProductInfo(product.getProductInfo());
        return bean;
    }

    @Override
    public Boolean updateCancelAudit(List<Long> productIds) {
        if (CollectionUtil.isNotEmpty(productIds)) {
            List<Product> productList = new ArrayList<>();
            for (Long productId : productIds) {
                Product bean = new Product();
                bean.setId(productId);
                bean.setAuditStatus(-1);
                productList.add(bean);
            }
            return this.updateBatch(productList);
        }
        return false;
    }

    @Override
    public List<Long> selectProductIds() {
        return baseMapper.selectProductIds();
    }
}

