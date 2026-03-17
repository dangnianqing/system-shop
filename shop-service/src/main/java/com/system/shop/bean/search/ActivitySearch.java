package com.system.shop.bean.search;

import com.shop.common.base.PageParam;
import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Data
public class ActivitySearch extends PageParam implements Serializable {

    private String activityName;
    private Long storeId;

    public Map<String, Object> querymap() {
        return new HashMap<String, Object>() {{
            put("activityName", activityName);
            put("storeId", storeId);

        }};
    }
}
