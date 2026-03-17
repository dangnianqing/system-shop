package com.system.shop.entity;

import com.system.shop.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 会员微信信息
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class MemberWx extends BaseEntity implements Serializable {
    /**
     * 会员ID
     */
    private Long memberId;

    /**
     * 微信公众号OPENGID
     */
    private String mpOpenId;

    /**
     * 微信公众号OPENGID
     */
    private String unionId;

    /**
     * 小程序OPENGID
     */
    private String miniAppOpenId;

    private static final long serialVersionUID = 1L;
}