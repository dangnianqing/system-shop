package com.system.shop.bean.search;

import cn.hutool.core.collection.CollectionUtil;
import com.github.pagehelper.util.StringUtil;
import com.github.pagehelper.PageParam;
import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author ：zhanghaijun
 * @Date ：Created in  2024/4/22 16:58
 * @Description：
 */
@Data
public class ProductSearch extends PageParam implements Serializable {


    private String productName;

    private String productCode;
    /**
     * 审核状态
     */
    private List<Integer> auditStatus;

    /**
     * 上架状态
     */
    private List<Integer> upDownStatus;

    /**
     * 一级商品分类
     */
    private Long categoryClassOne;

    /**
     * 二级商品分类
     */
    private Long categoryClassTwo;

    /**
     * 三级商品分类
     */
    private Long categoryClassThree;

    /**
     * 四级商品分类
     */
    private Long categoryClassFour;


    private Long storeId;

    private List<Long> storeIds;

    private Integer upDown;


    public Map<String, Object> querymap() {
        return new HashMap<String, Object>() {{
            if (StringUtil.isNotEmpty(productName)) {
                put("productName", productName);
            }
            if (StringUtil.isNotEmpty(productCode)) {
                put("productCode", productCode);
            }
            if (CollectionUtil.isNotEmpty(auditStatus)) {
                put("auditStatus", auditStatus);
            }
            if (CollectionUtil.isNotEmpty(upDownStatus)) {
                put("upDownStatus", upDownStatus);
            }
            if (categoryClassThree != null) {
                put("categoryClassThree", categoryClassThree);
            }
            if (categoryClassFour != null) {
                put("categoryClassFour", categoryClassFour);
            }
            if (storeId != null) {
                put("storeId", storeId);
            }
            if (CollectionUtil.isNotEmpty(storeIds)) {
                put("storeIds", storeIds);
            }
        }};
    }
}
