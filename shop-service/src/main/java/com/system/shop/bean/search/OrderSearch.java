package com.system.shop.bean.search;

import com.github.pagehelper.PageParam;
import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Data
public class OrderSearch extends PageParam implements Serializable {
    private Long memberId;
    private Long storeId;
    private String orderCode;
    private String orderStatus;
    private String mobile;
    private String userName;

    public Map<String, Object> querymap() {
        return new HashMap<String, Object>() {{
            put("memberId", memberId);
            put("mobile", mobile);
            put("userName", userName);
            put("storeId", storeId);
            put("orderCode", orderCode);
            put("orderStatus", orderStatus);

        }};
    }
}
