package com.system.shop.entity;

import com.system.shop.base.BaseEntity;
import com.system.shop.bean.Operator;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 订单日志
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class OrderLog extends BaseEntity implements Serializable {
    /**
     * 订单编号
     */
    private String orderCode;

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

    public OrderLog(String orderCode, String message, String operatorId, String operatorName, String operatorType) {
        this.orderCode = orderCode;
        this.message = message;
        this.operatorId = Long.valueOf(operatorId);
        this.operatorName = operatorName;
        this.operatorType = operatorType;
    }


    public OrderLog(String orderCode, String message, Operator operator) {
        this.orderCode = orderCode;
        this.message = message;
        this.operatorId = operator.getOperatorId();
        this.operatorName = operator.getOperatorName();
        this.operatorType = operator.getOperatorType();
    }
}