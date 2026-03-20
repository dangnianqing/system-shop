package com.system.shop.bean;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author ：zhanghaijun
 * @Date ：Created in  2024/4/19 08:46
 * @Description：
 */
@Data
public class WxLogin implements Serializable {

    private String code;
    private String avatarUrl;
    private String nickName;
    private String phoneCode;
}
