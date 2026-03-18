package com.system.shop.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.system.shop.base.BaseEntity;
import com.system.shop.bean.order.AfterSaleInfo;
import com.system.shop.enumer.AfterSaleRefundStatus;
import com.system.shop.enumer.AfterSaleStatus;
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
 * 售后订单
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class AfterSale extends BaseEntity implements Serializable {
    /**
     * 店铺ID
     */
    private Long storeId;

    /**
     * 店铺名称
     */
    private String storeName;

    /**
     * 会员ID
     */
    private Long memberId;

    /**
     * 订单编号
     */
    private String orderCode;

    /**
     * 订单售后编号
     */
    private String afterSaleCode;

    /**
     * 商品数量
     */
    private Integer afterSaleNum;

    /**
     * 退款总金额
     */
    private BigDecimal afterSalePrice;

    /**
     * 申请退款金额
     */
    private BigDecimal applyAfterSalePrice;

    /**
     * 售后状态
     */
    private String orderAfterStatus;

    /**
     * 售后类型
     */
    private String orderAfterType;

    /**
     * 售后原因Id
     */
    private Long reasonId;

    /**
     * 售后原因
     */
    private String reason;

    /**
     * 售后说明
     */
    private String explain;

    /**
     * 图片
     */
    private String image;

    /**
     * 申请时间
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date applyTime;

    /**
     * 退款状态
     */
    private String refundStatus;

    /**
     * 退款时间
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date refundTime;

    /**
     * 店铺备注
     */
    private String storeRemark;

    private static final long serialVersionUID = 1L;

    private List<AfterSaleItem> afterSaleItems;
    private String mobile;
    private String userName;

    public AfterSale(Order order, List<AfterSaleItem> afterSaleItems) {
        this.storeId = order.getStoreId();
        this.storeName = order.getStoreName();
        this.memberId = order.getMemberId();
        this.orderCode = order.getOrderCode();
        this.afterSaleItems = afterSaleItems;
    }

    public AfterSale(Order order, AfterSaleInfo afterSaleInfo) {
        this.storeId = order.getStoreId();
        this.storeName = order.getStoreName();
        this.memberId = order.getMemberId();
        this.orderCode = order.getOrderCode();
        this.reason = afterSaleInfo.getReason();
        this.reasonId = afterSaleInfo.getReasonId();
        this.explain = afterSaleInfo.getExplain();
        this.image = afterSaleInfo.getImage();
        this.orderAfterType = afterSaleInfo.getOrderAfterType();
        this.orderAfterStatus = AfterSaleStatus.APPLY.name();
        this.afterSaleCode = SnowFlake.getCode("A");
        this.applyTime = new Date();
        this.refundStatus = AfterSaleRefundStatus.REFUND_FAIL.name();

    }
}