package com.system.shop.entity;

import com.system.shop.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

/**
 * 地址区域
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class Region extends BaseEntity implements Serializable {
    /**
     * 城市区号
     */
    private String citycode;

    /**
     * 区域代码
     */
    private String adcode;

    /**
     * 区域名称
     */
    private String name;

    /**
     * 经纬度
     */
    private String center;

    /**
     * 层级
     */
    private String level;

    /**
     * 父区域代码
     */
    private Long parentId;

    private static final long serialVersionUID = 1L;

    private List<Region> districts;

    private Boolean leaf;
    private List<Region> children;
}