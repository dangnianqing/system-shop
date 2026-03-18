package com.system.shop.entity;

import com.system.shop.base.BaseEntity;
import com.system.shop.bean.cart.ProductSettlement;
import com.system.shop.enumer.CommentStatus;
import com.system.shop.enumer.OrderItemAfterSaleStatus;
import com.system.shop.utils.SnowFlake;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 门店订单明细行
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class OrderItem extends BaseEntity implements Serializable {
    /**
     * 订单编号
     */
    private String orderCode;

    /**
     * 订单明细行编号
     */
    private String orderItemCode;

    /**
     * 商品sku编码
     */
    private String productSkuCode;

    /**
     * 商品编码
     */
    private Long productSkuId;

    /**
     * sku规格信息
     */
    private String specInfo;

    /**
     * 商品编码
     */
    private String productName;

    /**
     * 商品名称
     */
    private String productCode;

    /**
     * 商品ID
     */
    private Long productId;

    /**
     * 商品主图
     */
    private String productImage;

    /**
     * 商品数量
     */
    private Integer productNum;

    /**
     * 商品销售价
     */
    private BigDecimal salePrice;

    /**
     * 商品总金额
     */
    private BigDecimal productPrice;

    /**
     * 优惠券金额
     */
    private BigDecimal couponPrice;

    /**
     * 促销活动金额
     */
    private BigDecimal activityPrice;

    /**
     * 优惠总金额
     */
    private BigDecimal discountPrice;

    /**
     * 结算金额
     */
    private BigDecimal promotionPrice;

    /**
     * 支付金额
     */
    private BigDecimal payPrice;

    /**
     * 评价状态
     */
    private String commentStatus;

    /**
     * 售后标记
     */
    private String afterSaleStatus;

    /**
     * 售后数量
     */
    private Integer afterSaleNum;

    /**
     * 售后数量
     */
    private BigDecimal afterSalePrice;

    private static final long serialVersionUID = 1L;

    public OrderItem(String orderCode, ProductSettlement productSettlement) {
        this.orderCode = orderCode;
        this.orderItemCode = SnowFlake.getCode("OI");
        this.productSkuCode = productSettlement.getProductSkuCode();
        this.productSkuId = productSettlement.getProductSkuId();
        this.specInfo = productSettlement.getSpecInfo();
        this.productName = productSettlement.getProductName();
        this.productCode = productSettlement.getProductCode();
        this.productId = productSettlement.getProductId();
        this.productImage = productSettlement.getImage();
        this.productNum = productSettlement.getNum();
        this.salePrice = productSettlement.getSalePrice();
        this.productPrice = productSettlement.getProductPrice();
        this.couponPrice = productSettlement.getCouponPrice();
        this.activityPrice = productSettlement.getActivityPrice();
        this.discountPrice = productSettlement.getDiscountPrice();
        this.promotionPrice = productSettlement.getPromotionPrice();
        this.payPrice = productSettlement.getPayPrice();
        this.commentStatus = CommentStatus.NEW.name();
        this.afterSaleStatus = OrderItemAfterSaleStatus.NEW.name();
        this.afterSaleNum = 0;
        this.afterSalePrice = BigDecimal.ZERO;
    }

    public OrderItem(String orderItemCode, String afterSaleStatus) {
        this.orderItemCode = orderItemCode;
        this.afterSaleStatus = afterSaleStatus;
    }

    public OrderItem(String orderItemCode, String afterSaleStatus, Integer afterSaleNum, BigDecimal afterSalePrice) {
        this.orderItemCode = orderItemCode;
        this.afterSaleStatus = afterSaleStatus;
        this.afterSaleNum = afterSaleNum;
        this.afterSalePrice = afterSalePrice;
    }
}