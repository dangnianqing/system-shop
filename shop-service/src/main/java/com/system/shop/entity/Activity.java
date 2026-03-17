package com.system.shop.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.system.shop.base.BaseEntity;
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
 * 促销活动
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class Activity extends BaseEntity implements Serializable {
    /**
     * 活动名称
     */
    private String name;

    /**
     * 活动类型
     */
    private Integer type;

    /**
     * 活动开始时间
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;

    /**
     * 活动结束时间
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;

    /**
     * 店铺ID
     */
    private Long storeId;

    /**
     * 优惠门槛件数
     */
    private Integer fullAmount;

    /**
     * 0 或者 1并且
     */
    private Integer fullType;

    /**
     * 优惠门槛金额
     */
    private BigDecimal fullMoney;

    /**
     * 折扣金额
     */
    private BigDecimal discountPrice;

    /**
     * 活动标签
     */
    private String tag;

    /**
     * 0 门店全部商品 1指定商品
     */
    private Integer scopeType;

    /**
     * 活动说明
     */
    private String description;

    private static final long serialVersionUID = 1L;


    private List<Long> productIds;

    private List<ActivityProduct> productList;
}