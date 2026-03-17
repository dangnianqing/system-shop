package com.system.shop.entity;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.Set;

@Data
public class SysUser {
    private Long id;
    private String userName;
    private String mobile;
    private String image;
    private Integer status;
    private String password;
    private LocalDateTime createTime;
    private String createBy;
    private String updateBy;
    private LocalDateTime updateTime;
    private Integer deleteFlag;
    private Set<SysRole> roles;

    @Override
    public String toString() {
        return "SysUser{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", mobile='" + mobile + '\'' +
                ", image='" + image + '\'' +
                ", status=" + status +
                ", password='" + password + '\'' +
                ", createTime=" + createTime +
                ", createBy='" + createBy + '\'' +
                ", updateBy='" + updateBy + '\'' +
                ", updateTime=" + updateTime +
                ", deleteFlag=" + deleteFlag +
                ", roles=" + roles +
                '}';
    }
} 