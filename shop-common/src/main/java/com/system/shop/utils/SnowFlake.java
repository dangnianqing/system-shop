package com.system.shop.utils;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;

/**
 * @Author ：zhanghaijun
 * @Date ：Created in  2024/4/17 16:46
 * @Description：
 */

public class SnowFlake {
    private static Snowflake snowflake;

    /**
     * 初始化配置
     *
     * @param workerId
     * @param datacenterId
     */
    public static void initialize(long workerId, long datacenterId) {
        snowflake = IdUtil.getSnowflake(workerId, datacenterId);
    }

    public static long getId() {
        return snowflake.nextId();
    }

    public static String getIdString() {
        return String.valueOf(snowflake.nextId());
    }

    public static String getCode(String prefix) {
        return String.valueOf(prefix +snowflake.nextId());
    }
}
