package com.system.shop.entity;

import com.system.shop.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 门店物流公司
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class StoreLogistics extends BaseEntity implements Serializable {
    /**
     * 门店ID
     */
    private Long storeId;

    /**
     * code
     */
    private Long logisticsId;

    /**
     * 状态1 可用
     */
    private Integer status;

    private static final long serialVersionUID = 1L;

    /**
     * code
     */
    private String logisticsCompanyCode;

    /**
     * name
     */
    private String logisticsCompanyName;


    public StoreLogistics(Long storeId, Long logisticsId, Integer status) {
        this.storeId = storeId;
        this.logisticsId = logisticsId;
        this.status = status;
    }

    public StoreLogistics(Logistics logistics) {
        this.status = 0;
        this.logisticsId = logistics.getId();
        this.logisticsCompanyCode = logistics.getLogisticsCompanyCode();
        this.logisticsCompanyName = logistics.getLogisticsCompanyName();
    }
}