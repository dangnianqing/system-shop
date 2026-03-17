package com.system.shop.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.system.shop.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class Store extends BaseEntity implements Serializable {
    public static final String STATUS_OPEN = "open";
    public static final String STATUS_CLOSE = "close";

    /**
     * 店铺logo
     */
    private String storeLogo;

    /**
     * 店铺名称
     */
    private String storeName;

    /**
     * 关联会员id
     */
    private Long memberId;

    /**
     * 门店状态
     */
    private String status;

    /**
     * 是否自营
     */
    private Integer selfType;

    /**
     * 开业时间
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date openTime;

    private static final long serialVersionUID = 1L;
}