package com.system.shop.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author ：zhanghaijun
 * @Date ：Created in  2024/5/9 13:51
 * @Description：
 */
@Data
public class ColorSize implements Serializable {
    private String code;
    private String name;


    public ColorSize(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public ColorSize() {
    }
}
