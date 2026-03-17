package com.system.shop.service.entity;

import lombok.Data;

@Data
public class SysUserRole {
    private Long id;
    private Long userId;
    private Long roleId;
    private String createTime;
    private String updateTime;
    private Integer deleteFlag;
} 