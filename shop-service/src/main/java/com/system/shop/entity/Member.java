package com.system.shop.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.system.shop.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 会员表
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class Member extends BaseEntity implements Serializable {
    /**
     * 手机号码
     */
    private String mobile;

    /**
     * 会员昵称
     */
    private String userName;

    /**
     * 生日
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthday;

    /**
     * 密码
     */
    private String password;

    /**
     * 会员性别
     */
    private Integer sex;

    /**
     * 会员头像
     */
    private String face;

    /**
     * 积分余额
     */
    private Integer integral;

    /**
     * 钱包余额
     */
    private BigDecimal wallet;

    /**
     * 最近登录时间
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date loginTime;

    private static final long serialVersionUID = 1L;


}