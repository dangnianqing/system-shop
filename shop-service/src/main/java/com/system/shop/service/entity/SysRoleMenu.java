package com.system.shop.service.entity;

import lombok.Data;

@Data
public class SysRoleMenu {
    private Long id;
    private Long roleId;
    private Long menuId;
    private String createTime;
    private String updateTime;
    private Integer deleteFlag;
} 