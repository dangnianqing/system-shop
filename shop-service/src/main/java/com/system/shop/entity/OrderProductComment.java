package com.system.shop.entity;

import com.system.shop.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 订单商品评价表
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class OrderProductComment extends BaseEntity implements Serializable {
    /**
     * 店铺ID
     */
    private Long storeId;

    /**
     * 店铺名称
     */
    private String storeName;

    /**
     * 商品ID
     */
    private Long productId;

    /**
     * 商品skuID
     */
    private Long productSkuId;

    /**
     * sku规格信息
     */
    private String specInfo;

    /**
     * 订单编号
     */
    private String orderCode;

    /**
     * 订单编号
     */
    private String orderItemCode;

    /**
     * 会员ID
     */
    private Long memberId;

    /**
     * 会员名称
     */
    private String memberName;

    /**
     * 会员头像
     */
    private String memberImage;

    /**
     * 评价内容
     */
    private String content;

    /**
     * 评价图片
     */
    private String contentImage;

    /**
     * 商品评分
     */
    private Integer productScore;

    /**
     * 物流评分
     */
    private Integer deliveryScore;

    /**
     * 服务评分
     */
    private Integer serviceScore;

    /**
     * 评价时间
     */
    private Date commentTime;

    /**
     * 回复状态
     */
    private Boolean replyStatus;

    /**
     * 回复内容
     */
    private String replyContent;

    /**
     * 回复图片
     */
    private String replyContentImage;

    /**
     * 回复时间
     */
    private Date replyTime;

    /**
     * 状态
     */
    private String status;

    private static final long serialVersionUID = 1L;
}