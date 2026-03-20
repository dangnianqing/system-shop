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
    private static final String REFRESH_TOKEN_PREFIX = "refresh:token:";
    private static final String USER_PREFIX = "user:token:";
    private static final String USER_REFRESH_PREFIX = "user:refresh:";

    @Value("${jwt.expiration}")
    private int jwtExpirationInMs;

    @Value("${jwt.refresh-expiration:604800000}")
    private int jwtRefreshExpirationInMs;

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
     * 将 refreshToken 存入 Redis
     * @param userId 用户ID
     * @param refreshToken 刷新 token
     */
    public void saveRefreshToken(Long userId, String refreshToken) {
        String refreshTokenKey = REFRESH_TOKEN_PREFIX + refreshToken;
        String userRefreshKey = USER_REFRESH_PREFIX + userId;

        // 存储 refreshToken -> userId 映射
        redisTemplate.opsForValue().set(refreshTokenKey, userId, jwtRefreshExpirationInMs, TimeUnit.MILLISECONDS);
        // 存储 userId -> refreshToken 映射
        redisTemplate.opsForValue().set(userRefreshKey, refreshToken, jwtRefreshExpirationInMs, TimeUnit.MILLISECONDS);
    }

    /**
     * 保存 accessToken 和 refreshToken
     * @param userId 用户ID
     * @param accessToken 访问 token
     * @param refreshToken 刷新 token
     */
    public void saveTokens(Long userId, String accessToken, String refreshToken) {
        saveToken(userId, accessToken);
        saveRefreshToken(userId, refreshToken);
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
     * 验证 refreshToken 是否在 Redis 中有效
     * @param refreshToken 刷新 token
     * @return 是否有效
     */
    public boolean validateRefreshTokenInRedis(String refreshToken) {
        String refreshTokenKey = REFRESH_TOKEN_PREFIX + refreshToken;
        return Boolean.TRUE.equals(redisTemplate.hasKey(refreshTokenKey));
    }

    /**
     * 从 Redis 获取用户ID（通过 refreshToken）
     * @param refreshToken 刷新 token
     * @return 用户ID，如果不存在返回 null
     */
    public Long getUserIdByRefreshToken(String refreshToken) {
        String refreshTokenKey = REFRESH_TOKEN_PREFIX + refreshToken;
        Object userId = redisTemplate.opsForValue().get(refreshTokenKey);
        return userId != null ? Long.valueOf(userId.toString()) : null;
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
     * 删除 refreshToken
     * @param refreshToken 刷新 token
     */
    public void removeRefreshToken(String refreshToken) {
        String refreshTokenKey = REFRESH_TOKEN_PREFIX + refreshToken;
        Object userId = redisTemplate.opsForValue().get(refreshTokenKey);

        if (userId != null) {
            String userRefreshKey = USER_REFRESH_PREFIX + userId;
            redisTemplate.delete(userRefreshKey);
        }

        redisTemplate.delete(refreshTokenKey);
    }

    /**
     * 根据用户ID删除 token（踢出用户时使用）
     * @param userId 用户ID
     */
    public void removeTokenByUserId(Long userId) {
        // 删除 accessToken
        String userTokenKey = USER_PREFIX + userId;
        Object token = redisTemplate.opsForValue().get(userTokenKey);
        if (token != null) {
            String tokenKey = TOKEN_PREFIX + token;
            redisTemplate.delete(tokenKey);
        }
        redisTemplate.delete(userTokenKey);

        // 删除 refreshToken
        String userRefreshKey = USER_REFRESH_PREFIX + userId;
        Object refreshToken = redisTemplate.opsForValue().get(userRefreshKey);
        if (refreshToken != null) {
            String refreshTokenKey = REFRESH_TOKEN_PREFIX + refreshToken;
            redisTemplate.delete(refreshTokenKey);
        }
        redisTemplate.delete(userRefreshKey);
    }
}
