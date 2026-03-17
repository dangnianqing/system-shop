package com.system.shop.entity;

import com.system.shop.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

/**
 * 门店管理员
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class StoreUser extends BaseEntity implements Serializable {
    /**
     * 门店ID
     */
    private Long storeId;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 密码
     */
    private String password;

    /**
     * 头像
     */
    private String image;

    /**
     * 状态1 可用
     */
    private Integer status;

    private static final long serialVersionUID = 1L;


    private List<Long> roleIds;


    private List<StoreMenu> menus;
}