package com.system.shop.entity.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 用户Excel导入导出实体类
 */
@Data
public class UserExcel {
    
    @ExcelProperty("用户ID")
    private Long id;
    
    @ExcelProperty("用户名")
    private String userName;
    
    @ExcelProperty("手机号")
    private String mobile;
    
    @ExcelProperty("状态")
    private Integer status;
    
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;
    
    @ExcelProperty("金额")
    private BigDecimal amount;
} 