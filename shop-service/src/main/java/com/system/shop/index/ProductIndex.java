package com.system.shop.index;

import com.system.shop.entity.Product;
import com.system.shop.entity.ProductSku;
import com.system.shop.entity.ProductSpec;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Document(indexName = "system_product_index")
@Data
public class ProductIndex implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Id
    private String id;
    private String productId;
    private String storeId;
    private String storeName;
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
    private String brandId;

    private String brandName;

    /**
     * 商品分类ID
     */
    private List<String> categoryIds;

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
     * 审核状态
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
     * 商品详情
     */
    private String productInfo;

    private List<String> images;

    private List<Sku> skuList;

    private List<Spec> specList;

    public ProductIndex(Product product, List<ProductSku> productSkus) {
        this.id = String.valueOf(product.getId());
        this.productId = String.valueOf(product.getId());
        this.storeId = String.valueOf(product.getStoreId());
        this.storeName = product.getStoreName();
        this.brandId = String.valueOf(product.getBrandId());
        this.productCode = product.getProductCode();
        this.productName = product.getProductName();
        this.categoryIds = Arrays.asList(String.valueOf(product.getCategoryClassOne()), String.valueOf(product.getCategoryClassTwo()), String.valueOf(product.getCategoryClassThree()), String.valueOf(product.getCategoryClassFour()));
        this.costPrice = product.getCostPrice();
        this.marketPrice = product.getMarketPrice();
        this.salePrice = product.getSalePrice();
        this.auditStatus = product.getAuditStatus();
        this.upDownStatus = product.getUpDownStatus();
        this.quantity = product.getQuantity();
        this.headImage = product.getHeadImage();
        this.yearCode = product.getYearCode();
        this.seasonCode = product.getSeasonCode();
        this.productInfo = product.getProductInfo();
        this.buyCount= product.getBuyCount();
        this.images = product.getImagePath();
        List<Sku> skus = new ArrayList<>();
        for (ProductSku productSku : productSkus) {
            skus.add(new Sku(productSku));
        }
        this.skuList = skus;
        List<Spec> specs = new ArrayList<>();
        for (ProductSpec productSpec : product.getSpecList()) {
            specs.add(new Spec(productSpec));
        }
        this.specList = specs;
    }

    public ProductIndex() {
    }
}
