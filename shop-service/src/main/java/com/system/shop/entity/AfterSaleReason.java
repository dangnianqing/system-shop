package com.system.shop.entity;

import com.system.shop.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 售后原因
 */
@Data
@EqualsAndHashCode(callSuper=true)
@NoArgsConstructor
public class AfterSaleReason extends BaseEntity implements Serializable {
    /**
    * 售后原因
    */
    private String reason;

    /**
    * 原因类型
    */
    private String serviceType;

    private static final long serialVersionUID = 1L;


    public AfterSaleReason(String reason, String serviceType) {
        this.reason = reason;
        this.serviceType = serviceType;
    }
}