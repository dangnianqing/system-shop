package com.system.shop.entity;

import com.system.shop.base.BaseEntity;
import com.system.shop.bean.order.AfterSaleItemInfo;
import com.system.shop.utils.SnowFlake;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 售后订单明细行
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class AfterSaleItem extends BaseEntity implements Serializable {
    /**
     * 订单售后编号
     */
    private String afterSaleCode;

    /**
     * 订单售后明细行编号
     */
    private String afterSaleItemCode;

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
     * 销售价
     */
    private BigDecimal salePrice;

    /**
     * 售后商品数量
     */
    private Integer afterSaleNum;

    /**
     * 退款金额
     */
    private BigDecimal afterSalePrice;

    /**
     * 申请退款金额
     */
    private BigDecimal applyAfterSalePrice;

    private static final long serialVersionUID = 1L;

    private Integer buyNum;

    private Integer afterNum;


    public AfterSaleItem(OrderItem orderItem) {
        this.orderItemCode = orderItem.getOrderItemCode();
        this.productCode = orderItem.getProductCode();
        this.productId = orderItem.getProductId();
        this.productImage = orderItem.getProductImage();
        this.productName = orderItem.getProductName();
        this.productSkuCode = orderItem.getProductSkuCode();
        this.productSkuId = orderItem.getProductSkuId();
        this.specInfo = orderItem.getSpecInfo();
        this.afterSalePrice = orderItem.getPayPrice();
        this.buyNum = orderItem.getProductNum();
        this.salePrice = orderItem.getSalePrice();
        this.afterNum = orderItem.getProductNum() - orderItem.getAfterSaleNum();
        this.afterSaleNum = orderItem.getProductNum() - orderItem.getAfterSaleNum();

    }

    public AfterSaleItem(String afterSaleCode, OrderItem orderItem, AfterSaleItemInfo afterSaleItemInfo) {
        this.afterSaleCode = afterSaleCode;
        this.afterSaleItemCode = SnowFlake.getCode("AI");
        this.orderItemCode = orderItem.getOrderItemCode();
        this.productCode = orderItem.getProductCode();
        this.productId = orderItem.getProductId();
        this.productImage = orderItem.getProductImage();
        this.productName = orderItem.getProductName();
        this.productSkuCode = orderItem.getProductSkuCode();
        this.productSkuId = orderItem.getProductSkuId();
        this.specInfo = orderItem.getSpecInfo();
        this.salePrice = orderItem.getSalePrice();
        this.afterSaleNum = afterSaleItemInfo.getAfterNum();
        if (afterSaleItemInfo.getAfterNum().equals(orderItem.getProductNum())) {
            this.applyAfterSalePrice = orderItem.getPayPrice();
        } else if (afterSaleItemInfo.getAfterNum().equals(orderItem.getProductNum() - orderItem.getAfterSaleNum())) {
            this.applyAfterSalePrice = orderItem.getPayPrice().subtract(orderItem.getAfterSalePrice());
        } else {
            this.applyAfterSalePrice = orderItem.getPayPrice().divide(BigDecimal.valueOf(orderItem.getProductNum())).setScale(2, BigDecimal.ROUND_DOWN).multiply(new BigDecimal(afterSaleItemInfo.getAfterNum()));
        }
        this.afterSalePrice = this.applyAfterSalePrice;
    }
}