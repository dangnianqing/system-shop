package com.system.shop.base;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.system.shop.annotation.DeleteFlag;
import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @Author ：zhanghaijun
 * @Date ：Created in  2024/4/10 16:21
 * @Description：
 */
@Data
public class Entity {
    /**
     * 创建时间
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @CreatedDate
    protected Date createTime;

    /**
     * 创建者
     */
    @CreatedBy
    protected String createBy;

    /**
     * 修改者
     */
    @LastModifiedBy
    protected String updateBy;

    /**
     * 修改时间
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @LastModifiedDate
    protected Date updateTime;

    /**
     * 删除标志 true/false 删除/未删除
     */
    @DeleteFlag
    protected Boolean deleteFlag;
}
