package com.system.shop.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Redis 工具类 - 基于 StringRedisTemplate，支持对象 JSON 序列化
 */
@Component
public class RedisUtil {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper()
            .setSerializationInclusion(JsonInclude.Include.NON_NULL)
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
            .disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);

    private static StringRedisTemplate stringRedisTemplate;

    @Autowired
    public void setStringRedisTemplate(StringRedisTemplate stringRedisTemplate) {
        RedisUtil.stringRedisTemplate = stringRedisTemplate;
    }

    // ==================== String 操作 ====================

    /**
     * 设置缓存
     */
    public static void set(String key, String value) {
        stringRedisTemplate.opsForValue().set(key, value);
    }

    /**
     * 设置缓存（带过期时间）
     */
    public static void set(String key, String value, long timeout, TimeUnit unit) {
        stringRedisTemplate.opsForValue().set(key, value, timeout, unit);
    }

    /**
     * 设置对象缓存（JSON 序列化）
     */
    public static <T> void setObject(String key, T object) {
        try {
            String json = OBJECT_MAPPER.writeValueAsString(object);
            stringRedisTemplate.opsForValue().set(key, json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Redis 序列化失败: " + e.getMessage(), e);
        }
    }

    /**
     * 设置对象缓存（带过期时间）
     */
    public static <T> void setObject(String key, T object, long timeout, TimeUnit unit) {
        try {
            String json = OBJECT_MAPPER.writeValueAsString(object);
            stringRedisTemplate.opsForValue().set(key, json, timeout, unit);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Redis 序列化失败: " + e.getMessage(), e);
        }
    }

    /**
     * 设置对象缓存（过期时间-秒）
     */
    public static <T> void setObject(String key, T object, long seconds) {
        setObject(key, object, seconds, TimeUnit.SECONDS);
    }

    /**
     * 获取缓存
     */
    public static String get(String key) {
        return stringRedisTemplate.opsForValue().get(key);
    }

    /**
     * 获取对象缓存（JSON 反序列化）
     */
    public static <T> T getObject(String key, Class<T> clazz) {
        String json = stringRedisTemplate.opsForValue().get(key);
        if (json == null) {
            return null;
        }
        try {
            return OBJECT_MAPPER.readValue(json, clazz);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Redis 反序列化失败: " + e.getMessage(), e);
        }
    }

    /**
     * 获取对象缓存（支持泛型，如 List<User>）
     */
    public static <T> T getObject(String key, TypeReference<T> typeReference) {
        String json = stringRedisTemplate.opsForValue().get(key);
        if (json == null) {
            return null;
        }
        try {
            return OBJECT_MAPPER.readValue(json, typeReference);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Redis 反序列化失败: " + e.getMessage(), e);
        }
    }

    /**
     * 删除缓存
     */
    public static Boolean delete(String key) {
        return stringRedisTemplate.delete(key);
    }

    /**
     * 批量删除缓存
     */
    public static Long delete(Collection<String> keys) {
        return stringRedisTemplate.delete(keys);
    }

    /**
     * 判断 key 是否存在
     */
    public static Boolean hasKey(String key) {
        return stringRedisTemplate.hasKey(key);
    }

    /**
     * 设置过期时间
     */
    public static Boolean expire(String key, long timeout, TimeUnit unit) {
        return stringRedisTemplate.expire(key, timeout, unit);
    }

    /**
     * 获取过期时间
     */
    public static Long getExpire(String key, TimeUnit unit) {
        return stringRedisTemplate.getExpire(key, unit);
    }

    /**
     * 自增
     */
    public static Long increment(String key) {
        return stringRedisTemplate.opsForValue().increment(key);
    }

    /**
     * 自增指定值
     */
    public static Long increment(String key, long delta) {
        return stringRedisTemplate.opsForValue().increment(key, delta);
    }

    /**
     * 自减
     */
    public static Long decrement(String key) {
        return stringRedisTemplate.opsForValue().decrement(key);
    }

    /**
     * 自减指定值
     */
    public static Long decrement(String key, long delta) {
        return stringRedisTemplate.opsForValue().decrement(key, delta);
    }

    // ==================== Hash 操作 ====================

    /**
     * Hash 设置字段
     */
    public static void hSet(String key, String field, String value) {
        stringRedisTemplate.opsForHash().put(key, field, value);
    }

    /**
     * Hash 设置对象字段
     */
    public static <T> void hSetObject(String key, String field, T object) {
        try {
            String json = OBJECT_MAPPER.writeValueAsString(object);
            stringRedisTemplate.opsForHash().put(key, field, json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Redis 序列化失败: " + e.getMessage(), e);
        }
    }

    /**
     * Hash 批量设置字段
     */
    public static void hSetAll(String key, Map<String, String> map) {
        stringRedisTemplate.opsForHash().putAll(key, map);
    }

    /**
     * Hash 获取字段
     */
    public static String hGet(String key, String field) {
        Object value = stringRedisTemplate.opsForHash().get(key, field);
        return value != null ? value.toString() : null;
    }

    /**
     * Hash 获取对象字段
     */
    public static <T> T hGetObject(String key, String field, Class<T> clazz) {
        Object value = stringRedisTemplate.opsForHash().get(key, field);
        if (value == null) {
            return null;
        }
        try {
            return OBJECT_MAPPER.readValue(value.toString(), clazz);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Redis 反序列化失败: " + e.getMessage(), e);
        }
    }

    /**
     * Hash 获取所有字段
     */
    public static Map<Object, Object> hGetAll(String key) {
        return stringRedisTemplate.opsForHash().entries(key);
    }

    /**
     * Hash 删除字段
     */
    public static Long hDelete(String key, Object... fields) {
        return stringRedisTemplate.opsForHash().delete(key, fields);
    }

    /**
     * Hash 判断字段是否存在
     */
    public static Boolean hHasKey(String key, String field) {
        return stringRedisTemplate.opsForHash().hasKey(key, field);
    }

    // ==================== List 操作 ====================

    /**
     * List 左推入
     */
    public static Long lPush(String key, String value) {
        return stringRedisTemplate.opsForList().leftPush(key, value);
    }

    /**
     * List 左推入对象
     */
    public static <T> Long lPushObject(String key, T object) {
        try {
            String json = OBJECT_MAPPER.writeValueAsString(object);
            return stringRedisTemplate.opsForList().leftPush(key, json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Redis 序列化失败: " + e.getMessage(), e);
        }
    }

    /**
     * List 右推入
     */
    public static Long rPush(String key, String value) {
        return stringRedisTemplate.opsForList().rightPush(key, value);
    }

    /**
     * List 右推入对象
     */
    public static <T> Long rPushObject(String key, T object) {
        try {
            String json = OBJECT_MAPPER.writeValueAsString(object);
            return stringRedisTemplate.opsForList().rightPush(key, json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Redis 序列化失败: " + e.getMessage(), e);
        }
    }

    /**
     * List 左弹出
     */
    public static String lPop(String key) {
        return stringRedisTemplate.opsForList().leftPop(key);
    }

    /**
     * List 左弹出对象
     */
    public static <T> T lPopObject(String key, Class<T> clazz) {
        String json = stringRedisTemplate.opsForList().leftPop(key);
        if (json == null) {
            return null;
        }
        try {
            return OBJECT_MAPPER.readValue(json, clazz);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Redis 反序列化失败: " + e.getMessage(), e);
        }
    }

    /**
     * List 获取范围元素
     */
    public static List<String> lRange(String key, long start, long end) {
        return stringRedisTemplate.opsForList().range(key, start, end);
    }

    /**
     * List 获取长度
     */
    public static Long lSize(String key) {
        return stringRedisTemplate.opsForList().size(key);
    }

    // ==================== Set 操作 ====================

    /**
     * Set 添加元素
     */
    public static Long sAdd(String key, String... values) {
        return stringRedisTemplate.opsForSet().add(key, values);
    }

    /**
     * Set 获取所有元素
     */
    public static Set<String> sMembers(String key) {
        return stringRedisTemplate.opsForSet().members(key);
    }

    /**
     * Set 移除元素
     */
    public static Long sRemove(String key, Object... values) {
        return stringRedisTemplate.opsForSet().remove(key, values);
    }

    /**
     * Set 判断元素是否存在
     */
    public static Boolean sIsMember(String key, Object value) {
        return stringRedisTemplate.opsForSet().isMember(key, value);
    }

    /**
     * Set 获取大小
     */
    public static Long sSize(String key) {
        return stringRedisTemplate.opsForSet().size(key);
    }

    // ==================== ZSet 操作 ====================

    /**
     * ZSet 添加元素
     */
    public static Boolean zAdd(String key, String value, double score) {
        return stringRedisTemplate.opsForZSet().add(key, value, score);
    }

    /**
     * ZSet 获取范围元素
     */
    public static Set<String> zRange(String key, long start, long end) {
        return stringRedisTemplate.opsForZSet().range(key, start, end);
    }

    /**
     * ZSet 移除元素
     */
    public static Long zRemove(String key, Object... values) {
        return stringRedisTemplate.opsForZSet().remove(key, values);
    }

    /**
     * ZSet 获取元素分数
     */
    public static Double zScore(String key, Object value) {
        return stringRedisTemplate.opsForZSet().score(key, value);
    }

    // ==================== 通用操作 ====================

    /**
     * 模糊查询 key
     */
    public static Set<String> keys(String pattern) {
        return stringRedisTemplate.keys(pattern);
    }

    /**
     * 获取 key 的类型
     */
    public static String type(String key) {
        return stringRedisTemplate.type(key).code();
    }
}
