package com.system.shop.entity;

import com.system.shop.base.BaseEntity;
import com.system.shop.bean.cart.ShoppSettlement;
import com.system.shop.utils.SnowFlake;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 订单头表
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class OrderHeader extends BaseEntity implements Serializable {
    /**
     * 订单头编号
     */
    private String orderHeaderCode;

    /**
     * 会员ID
     */
    private Long memberId;

    /**
     * 商品数量
     */
    private Integer productNum;

    /**
     * 总金额
     */
    private BigDecimal totalPrice;

    /**
     * 商品总金额
     */
    private BigDecimal productPrice;

    /**
     * 运费总金额
     */
    private BigDecimal freightPrice;

    /**
     * 优惠券总金额
     */
    private BigDecimal couponPrice;

    /**
     * 促销活动总金额
     */
    private BigDecimal activityPrice;

    /**
     * 优惠总金额
     */
    private BigDecimal discountPrice;

    /**
     * 支付总金额
     */
    private BigDecimal payPrice;

    private static final long serialVersionUID = 1L;


    public OrderHeader(ShoppSettlement shoppSettlement) {
        this.orderHeaderCode = SnowFlake.getCode("T");
        this.memberId = shoppSettlement.getMemberId();
        this.productNum = shoppSettlement.getProductNum();
        this.productPrice = shoppSettlement.getProductPrice();
        this.couponPrice = shoppSettlement.getCouponPrice();
        this.activityPrice = shoppSettlement.getActivityPrice();
        this.freightPrice = shoppSettlement.getFreightPrice();
        this.totalPrice = shoppSettlement.getTotalPrice();
        this.discountPrice = shoppSettlement.getDiscountPrice();
        this.payPrice = shoppSettlement.getPayPrice();
    }
}