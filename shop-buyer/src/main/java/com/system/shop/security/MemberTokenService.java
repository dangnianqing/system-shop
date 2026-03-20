package com.system.shop.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * 会员 Token 服务 - 管理 Redis 中的会员 Token
 */
@Service
public class MemberTokenService {

    private static final String TOKEN_PREFIX = "member:token:";
    private static final String REFRESH_TOKEN_PREFIX = "member:refresh:token:";
    private static final String MEMBER_PREFIX = "member:id:token:";
    private static final String MEMBER_REFRESH_PREFIX = "member:id:refresh:";

    @Value("${jwt.expiration}")
    private int jwtExpirationInMs;

    @Value("${jwt.refresh-expiration:604800000}")
    private int jwtRefreshExpirationInMs;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 保存 accessToken
     */
    public void saveToken(Long memberId, String token) {
        String tokenKey = TOKEN_PREFIX + token;
        String memberTokenKey = MEMBER_PREFIX + memberId;

        redisTemplate.opsForValue().set(tokenKey, memberId, jwtExpirationInMs, TimeUnit.MILLISECONDS);
        redisTemplate.opsForValue().set(memberTokenKey, token, jwtExpirationInMs, TimeUnit.MILLISECONDS);
    }

    /**
     * 保存 refreshToken
     */
    public void saveRefreshToken(Long memberId, String refreshToken) {
        String refreshTokenKey = REFRESH_TOKEN_PREFIX + refreshToken;
        String memberRefreshKey = MEMBER_REFRESH_PREFIX + memberId;

        redisTemplate.opsForValue().set(refreshTokenKey, memberId, jwtRefreshExpirationInMs, TimeUnit.MILLISECONDS);
        redisTemplate.opsForValue().set(memberRefreshKey, refreshToken, jwtRefreshExpirationInMs, TimeUnit.MILLISECONDS);
    }

    /**
     * 同时保存 accessToken 和 refreshToken
     */
    public void saveTokens(Long memberId, String accessToken, String refreshToken) {
        saveToken(memberId, accessToken);
        saveRefreshToken(memberId, refreshToken);
    }

    /**
     * 从 Token 获取会员ID
     */
    public Long getMemberIdByToken(String token) {
        String tokenKey = TOKEN_PREFIX + token;
        Object memberId = redisTemplate.opsForValue().get(tokenKey);
        return memberId != null ? Long.valueOf(memberId.toString()) : null;
    }

    /**
     * 从 refreshToken 获取会员ID
     */
    public Long getMemberIdByRefreshToken(String refreshToken) {
        String refreshTokenKey = REFRESH_TOKEN_PREFIX + refreshToken;
        Object memberId = redisTemplate.opsForValue().get(refreshTokenKey);
        return memberId != null ? Long.valueOf(memberId.toString()) : null;
    }

    /**
     * 验证 Token 是否有效
     */
    public boolean validateTokenInRedis(String token) {
        String tokenKey = TOKEN_PREFIX + token;
        return Boolean.TRUE.equals(redisTemplate.hasKey(tokenKey));
    }

    /**
     * 验证 refreshToken 是否有效
     */
    public boolean validateRefreshTokenInRedis(String refreshToken) {
        String refreshTokenKey = REFRESH_TOKEN_PREFIX + refreshToken;
        return Boolean.TRUE.equals(redisTemplate.hasKey(refreshTokenKey));
    }

    /**
     * 删除 Token
     */
    public void removeToken(String token) {
        String tokenKey = TOKEN_PREFIX + token;
        Object memberId = redisTemplate.opsForValue().get(tokenKey);

        if (memberId != null) {
            String memberTokenKey = MEMBER_PREFIX + memberId;
            redisTemplate.delete(memberTokenKey);
        }
        redisTemplate.delete(tokenKey);
    }

    /**
     * 删除 refreshToken
     */
    public void removeRefreshToken(String refreshToken) {
        String refreshTokenKey = REFRESH_TOKEN_PREFIX + refreshToken;
        Object memberId = redisTemplate.opsForValue().get(refreshTokenKey);

        if (memberId != null) {
            String memberRefreshKey = MEMBER_REFRESH_PREFIX + memberId;
            redisTemplate.delete(memberRefreshKey);
        }
        redisTemplate.delete(refreshTokenKey);
    }

    /**
     * 根据会员ID删除所有 Token
     */
    public void removeTokenByMemberId(Long memberId) {
        // 删除 accessToken
        String memberTokenKey = MEMBER_PREFIX + memberId;
        Object token = redisTemplate.opsForValue().get(memberTokenKey);
        if (token != null) {
            String tokenKey = TOKEN_PREFIX + token;
            redisTemplate.delete(tokenKey);
        }
        redisTemplate.delete(memberTokenKey);

        // 删除 refreshToken
        String memberRefreshKey = MEMBER_REFRESH_PREFIX + memberId;
        Object refreshToken = redisTemplate.opsForValue().get(memberRefreshKey);
        if (refreshToken != null) {
            String refreshTokenKey = REFRESH_TOKEN_PREFIX + refreshToken;
            redisTemplate.delete(refreshTokenKey);
        }
        redisTemplate.delete(memberRefreshKey);
    }
}
