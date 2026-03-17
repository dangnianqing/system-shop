package com.system.shop.bean.search;

import com.github.pagehelper.util.StringUtil;
import com.shop.common.base.PageParam;
import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author ：zhanghaijun
 * @Date ：Created in  2024/4/22 16:58
 * @Description：
 */
@Data
public class StoreSearch extends PageParam implements Serializable {
    private String storeName;

    private String status;

    public Map<String, Object> querymap() {
        return new HashMap<String, Object>() {{
            if (StringUtil.isNotEmpty(status)) {
                put("status", status);
            }
            if (StringUtil.isNotEmpty(storeName)) {
                put("storeName", storeName);
            }
        }};
    }

    public StoreSearch(String storeName, String status) {
        this.storeName = storeName;
        this.status = status;
    }

}
