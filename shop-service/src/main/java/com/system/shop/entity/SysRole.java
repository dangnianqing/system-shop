package com.system.shop.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class SysRole {
    private Long id;
    private String name;
    private String remark;
    private LocalDateTime createTime;
    private String createBy;
    private String updateBy;
    private LocalDateTime updateTime;
    private Integer deleteFlag;
} 