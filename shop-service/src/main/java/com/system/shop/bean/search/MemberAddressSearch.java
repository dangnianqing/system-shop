package com.system.shop.bean.search;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.github.pagehelper.PageParam;
import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class MemberAddressSearch extends PageParam implements Serializable {
    private Long memberId;

    public Map<String, Object> querymap() {
        return new HashMap<String, Object>() {{
            put("memberId", memberId);
        }};
    }
}
