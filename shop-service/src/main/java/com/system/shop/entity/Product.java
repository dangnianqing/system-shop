package com.system.shop.entity;

import com.system.shop.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 商品表
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class Product extends BaseEntity implements Serializable {
    /**
     * 商品名称
     */
    private String productName;

    /**
     * 商品名称
     */
    private String productCode;

    /**
     * 品牌ID
     */
    private Long brandId;

    /**
     * 一级商品分类
     */
    private Long categoryClassOne;

    /**
     * 二级商品分类
     */
    private Long categoryClassTwo;

    /**
     * 三级商品分类
     */
    private Long categoryClassThree;

    /**
     * 四级商品分类
     */
    private Long categoryClassFour;

    /**
     * 购买数量
     */
    private Integer buyCount;

    /**
     * 成本价格
     */
    private BigDecimal costPrice;

    /**
     * 销售价格
     */
    private BigDecimal salePrice;

    /**
     * 吊牌价格
     */
    private BigDecimal marketPrice;

    /**
     * 审核状态 -1 未提交审核 0 待审核 1 审核通过 2 审核不通过
     */
    private Integer auditStatus;

    /**
     * 上架状态
     */
    private Integer upDownStatus;

    /**
     * 库存
     */
    private Integer quantity;

    /**
     * 店铺ID
     */
    private Long storeId;

    /**
     * 门店名称
     */
    private String storeName;

    /**
     * 头图
     */
    private String headImage;

    /**
     * 年份
     */
    private String yearCode;

    /**
     * 季节
     */
    private String seasonCode;

    /**
     * 商品规格id 逗号隔开
     */
    private String specId;

    /**
     * 运费模板ID
     */
    private Long freightTemplateId;

    /**
     * 重量
     */
    private BigDecimal weight;

    /**
     * 商品详情
     */
    private String productInfo;

    private static final long serialVersionUID = 1L;

    private String categoryClassName;
    private List<Long> categoryClassIds;
    private List<String> imagePath;
    private List<ProductSku> skuList;
    private List<ProductSpec> specList;

    public Product(Long id, Integer upDownStatus) {
        this.id = id;
        this.upDownStatus = upDownStatus;
    }
}