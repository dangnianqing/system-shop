package com.system.shop.entity;

import com.system.shop.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 会员开票信息
 */
@Data
@EqualsAndHashCode(callSuper=true)
@AllArgsConstructor
@NoArgsConstructor
public class MemberReceipt extends BaseEntity implements Serializable {
    /**
    * 会员ID
    */
    private Long memberId;

    /**
    * 发票抬头 0个人 1公司
    */
    private Integer title;

    /**
    * 姓名
    */
    private String name;

    /**
    * 手机号/纳税人识别号
    */
    private String number;

    /**
    * 电子邮箱
    */
    private String email;

    /**
    * 发票内容0商品明细1商品类别
    */
    private Integer content;

    private static final long serialVersionUID = 1L;
}