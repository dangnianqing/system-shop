package com.system.shop.bean.search;



import com.github.pagehelper.PageParam;
import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Data
public class LogisticsSearch extends PageParam implements Serializable {
    /**
     * code
     */
    private String logisticsCompanyCode;

    /**
     * name
     */
    private String logisticsCompanyName;

    /**
     * 状态1 可用
     */
    private Integer status;

    public  Map<String, Object> querymap() {
        return new HashMap<String, Object>() {{
            put("logisticsCompanyCode", logisticsCompanyCode);
            put("logisticsCompanyName", logisticsCompanyName);
            put("status", status);

        }};
    }
}
