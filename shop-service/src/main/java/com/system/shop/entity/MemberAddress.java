package com.system.shop.entity;

import com.system.shop.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 会员收货地址
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class MemberAddress extends BaseEntity implements Serializable {
    /**
     * 会员ID
     */
    private Long memberId;

    /**
     * 手机号码
     */
    private String mobile;

    /**
     * 收货人姓名
     */
    private String name;

    /**
     * 路径
     */
    private String pathName;

    /**
     * 路径
     */
    private String pathId;

    /**
     * 详细地址
     */
    private String detail;

    /**
     * 是否为默认收货地址
     */
    private Boolean isDefault;

    private static final long serialVersionUID = 1L;
}