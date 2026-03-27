package com.system.shop.entity;

import java.util.Date;

import com.system.shop.base.BaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 会员表
 */
@Data
@NoArgsConstructor
public class Member extends BaseEntity implements java.io.Serializable {
    private static final long serialVersionUID = 1L;
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
    private Date birthday;

    /**
     * 姓名
     */
    private String name;

    /**
     * 会员积分
     */
    private Integer points;

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
     * 最近登录时间
     */
    private Date loginTime;

}