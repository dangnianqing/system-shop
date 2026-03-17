package com.system.shop.entity;

import com.system.shop.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 物流公司
 */
@Data
@EqualsAndHashCode(callSuper=true)
@AllArgsConstructor
@NoArgsConstructor
public class Logistics extends BaseEntity implements Serializable {
    /**
    * code
    */
    private String logisticsCompanyCode;

    /**
    * name
    */
    private String logisticsCompanyName;

    /**
    * 状态1 可用
    */
    private Integer status;

    private static final long serialVersionUID = 1L;
}