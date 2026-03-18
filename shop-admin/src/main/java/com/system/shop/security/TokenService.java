package com.system.shop.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * Token 服务 - 管理 Redis 中的 Token
 */
@Service
public class TokenService {

    private static final String TOKEN_PREFIX = "token:";
    private static final String USER_PREFIX = "user:token:";

    @Value("${jwt.expiration}")
    private int jwtExpirationInMs;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 将 token 存入 Redis
     * @param userId 用户ID
     * @param token JWT token
     */
    public void saveToken(Long userId, String token) {
        String tokenKey = TOKEN_PREFIX + token;
        String userTokenKey = USER_PREFIX + userId;
        
        // 存储 token -> userId 映射
        redisTemplate.opsForValue().set(tokenKey, userId, jwtExpirationInMs, TimeUnit.MILLISECONDS);
        // 存储 userId -> token 映射（用于踢出用户时删除旧 token）
        redisTemplate.opsForValue().set(userTokenKey, token, jwtExpirationInMs, TimeUnit.MILLISECONDS);
    }

    /**
     * 从 Redis 获取用户ID
     * @param token JWT token
     * @return 用户ID，如果不存在返回 null
     */
    public Long getUserIdByToken(String token) {
        String tokenKey = TOKEN_PREFIX + token;
        Object userId = redisTemplate.opsForValue().get(tokenKey);
        return userId != null ? Long.valueOf(userId.toString()) : null;
    }

    /**
     * 验证 token 是否在 Redis 中有效
     * @param token JWT token
     * @return 是否有效
     */
    public boolean validateTokenInRedis(String token) {
        String tokenKey = TOKEN_PREFIX + token;
        return Boolean.TRUE.equals(redisTemplate.hasKey(tokenKey));
    }

    /**
     * 删除 token（登出时使用）
     * @param token JWT token
     */
    public void removeToken(String token) {
        String tokenKey = TOKEN_PREFIX + token;
        Object userId = redisTemplate.opsForValue().get(tokenKey);
        
        if (userId != null) {
            String userTokenKey = USER_PREFIX + userId;
            redisTemplate.delete(userTokenKey);
        }
        
        redisTemplate.delete(tokenKey);
    }

    /**
     * 根据用户ID删除 token（踢出用户时使用）
     * @param userId 用户ID
     */
    public void removeTokenByUserId(Long userId) {
        String userTokenKey = USER_PREFIX + userId;
        Object token = redisTemplate.opsForValue().get(userTokenKey);
        
        if (token != null) {
            String tokenKey = TOKEN_PREFIX + token;
            redisTemplate.delete(tokenKey);
        }
        
        redisTemplate.delete(userTokenKey);
    }
}
