package com.system.shop.entity;

import com.system.shop.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

/**
 * 管理系统用户表
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysUser extends BaseEntity implements Serializable {
    /**
     * 用户名
     */
    private String userName;

    /**
     * 手机
     */
    private String mobile;

    /**
     * 头像
     */
    private String image;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 密码
     */
    private String password;

    private List<Long> roleIds;

    private Set<SysRole> roles;


    private static final long serialVersionUID = 1L;

    public SysUser(String userName) {
        this.userName = userName;
    }

    public SysUser(Long id, Integer status) {
        this.id = id;
        this.status = status;
    }

    public SysUser() {
    }

}