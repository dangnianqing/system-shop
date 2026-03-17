package com.system.shop.entity;

import com.system.shop.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 积分历史
 */
@Data
@EqualsAndHashCode(callSuper=true)
@AllArgsConstructor
@NoArgsConstructor
public class MemberIntegralHistory extends BaseEntity implements Serializable {
    /**
    * 会员ID
    */
    private Long memberId;

    /**
    * 消费之前积分
    */
    private Integer before;

    /**
    * 内容
    */
    private String content;

    /**
    * 消费积分
    */
    private Long integral;

    /**
    * 当前积分
    */
    private Integer current;

    /**
    * 消费积分类型
    */
    private String type;

    private static final long serialVersionUID = 1L;
}