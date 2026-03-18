package com.system.shop.bean.search;

import com.github.pagehelper.PageParam;
import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Data
public class MemberIntegralSearch extends PageParam implements Serializable {
    private Long memberId;

    public Map<String, Object> querymap() {
        return new HashMap<String, Object>() {{
            put("memberId", memberId);


        }};
    }
}
