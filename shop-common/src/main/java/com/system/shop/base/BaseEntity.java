package com.system.shop.base;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import org.springframework.data.annotation.Id;

/**
 * @Author ：zhanghaijun
 * @Date ：Created in  2024/4/10 16:21
 * @Description：
 */
@Data
public class BaseEntity extends Entity{
    /**
     * 主键ID
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @Id
    protected Long id;

}
