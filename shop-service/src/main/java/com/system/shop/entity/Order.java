package com.system.shop.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.shop.bean.cart.StoreSettlement;
import com.system.shop.base.BaseEntity;
import com.system.shop.enumer.OrderStatus;
import com.system.shop.enumer.PayStatus;
import com.system.shop.utils.SnowFlake;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 门店订单表
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class Order extends BaseEntity implements Serializable {
    /**
     * 店铺ID
     */
    private Long storeId;

    /**
     * 店铺名称
     */
    private String storeName;

    /**
     * 订单编号
     */
    private String orderCode;

    /**
     * 订单头订单编号
     */
    private String orderHeaderCode;

    /**
     * 下单时间
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date orderTime;

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

    /**
     * 订单状态
     */
    private String orderStatus;

    /**
     * 付款状态
     */
    private String payStatus;

    /**
     * 支付方式
     */
    private String payMethod;

    /**
     * 支付时间
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date payTime;

    /**
     * 支付方式返回的交易号
     */
    private String payNo;

    /**
     * 买家订单备注
     */
    private String remark;

    /**
     * 收货人姓名
     */
    private String receiveName;

    /**
     * 手机号码
     */
    private String receiveMobile;

    /**
     * 收货地址
     */
    private String receivePathName;

    /**
     * 收货地址
     */
    private String receivePathId;

    /**
     * 详细地址
     */
    private String receivePathDetail;

    /**
     * 是否可取消 true/false 可以/不可以
     */
    private Integer cancel;

    /**
     * 取消时间
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date cancelTime;

    /**
     * 订单取消原因
     */
    private String cancelReason;

    private static final long serialVersionUID = 1L;

    private List<OrderItem> orderItems;
    private String mobile;
    private String userName;
    private List<OrderLogistics> orderLogisticsList;
    private Long countDownTime;

    public Order(String orderHeaderCode, StoreSettlement storeSettlement, Member member, MemberAddress memberAddress) {
        this.orderHeaderCode = orderHeaderCode;
        this.orderCode = SnowFlake.getCode("O");
        this.storeId = storeSettlement.getStoreId();
        this.storeName = storeSettlement.getStoreName();
        this.memberId = member.getId();
        this.productNum = storeSettlement.getProductNum();
        this.productPrice = storeSettlement.getProductPrice();
        this.couponPrice = storeSettlement.getCouponPrice();
        this.activityPrice = storeSettlement.getActivityPrice();
        this.freightPrice = storeSettlement.getFreightPrice();
        this.payPrice = storeSettlement.getPayPrice();
        this.totalPrice = storeSettlement.getTotalPrice();
        this.discountPrice = storeSettlement.getDiscountPrice();
        this.orderStatus = OrderStatus.PENDING_PAYMENT.name();
        this.payStatus = PayStatus.UNPAID.name();
        this.remark = storeSettlement.getRemark();
        this.receiveName = memberAddress.getName();
        this.receiveMobile = memberAddress.getMobile();
        this.receivePathId = memberAddress.getPathId();
        this.receivePathName = memberAddress.getPathName();
        this.receivePathDetail = memberAddress.getDetail();
        this.cancel = 1;
        this.orderTime = new Date();

    }
}