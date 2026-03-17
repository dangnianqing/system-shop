package com.system.shop.entity;

import com.system.shop.base.BaseEntity;
import lombok.Data;

import java.io.Serializable;

/**
 * 首页轮播图
 */
@Data
public class IndexImage extends BaseEntity implements Serializable {


    /**
     * 图片名称
     */
    private String imageName;

    /**
     * 图片地址
     */
    private String imagePath;

    /**
     * 图片顺序
     */
    private Integer imageIndex;


    private static final long serialVersionUID = 1L;
}