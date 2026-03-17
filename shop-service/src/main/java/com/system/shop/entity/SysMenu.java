package com.system.shop.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class SysMenu {
    private Long id;
    private Long parentId;
    private String name;
    private String path;
    private String component;
    private String perms;
    private Integer type;
    private String icon;
    private Integer orderNum;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private Integer deleteFlag;
} 