package com.system.shop.bean;

import lombok.Data;

import java.io.Serializable;

@Data
public class Operator implements Serializable {
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

    public Operator(Long operatorId, String operatorName, String operatorType) {
        this.operatorId = operatorId;
        this.operatorName = operatorName;
        this.operatorType = operatorType;
    }
}
