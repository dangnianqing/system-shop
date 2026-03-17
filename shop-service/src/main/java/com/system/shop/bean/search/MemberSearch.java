package com.system.shop.bean.search;

import com.shop.common.base.PageParam;
import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Data
public class MemberSearch extends PageParam implements Serializable {
    private String mobile;
    private String userName;
    private String birthday;
    private Integer sex;
    private String loginTime;

    public Map<String, Object> querymap() {
        return new HashMap<String, Object>() {{
            put("mobile", mobile);
            put("userName", userName);
            put("birthday", birthday);
            put("sex", sex);
            put("loginTime", loginTime);


        }};
    }
}
