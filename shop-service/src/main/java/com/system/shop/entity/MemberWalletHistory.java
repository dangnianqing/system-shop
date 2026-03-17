package com.system.shop.entity;

import com.system.shop.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 余额历史
 */
@Data
@EqualsAndHashCode(callSuper=true)
@AllArgsConstructor
@NoArgsConstructor
public class MemberWalletHistory extends BaseEntity implements Serializable {
    /**
    * 会员ID
    */
    private Long memberId;

    /**
    * 消费之前余额
    */
    private BigDecimal before;

    /**
    * 内容
    */
    private String content;

    /**
    * 消费余额
    */
    private BigDecimal wallet;

    /**
    * 当前余额
    */
    private BigDecimal current;

    /**
    * 消费类型
    */
    private String type;

    private static final long serialVersionUID = 1L;

    private String value;
}