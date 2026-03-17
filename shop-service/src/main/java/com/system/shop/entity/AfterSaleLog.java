package com.system.shop.entity;

import com.system.shop.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 售后订单日志
 */
@Data
@EqualsAndHashCode(callSuper=true)
@AllArgsConstructor
@NoArgsConstructor
public class AfterSaleLog extends BaseEntity implements Serializable {
    /**
    * 售后订单编号
    */
    private String afterSaleCode;

    /**
    * 日志信息
    */
    private String message;

    /**
    * 操作者ID
    */
    private Long operatorId;

    /**
    * 操作者名称
    */
    private String operatorName;

    /**
    * 操作者类型
    */
    private String operatorType;

    private static final long serialVersionUID = 1L;
}