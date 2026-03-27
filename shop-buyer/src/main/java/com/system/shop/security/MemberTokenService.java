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
    private static final String MEMBER_PREFIX = "member:id:token:";

    @Value("${jwt.expiration}")
    private int jwtExpirationInMs;

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
     * 从 Token 获取会员ID
     */
    public Long getMemberIdByToken(String token) {
        String tokenKey = TOKEN_PREFIX + token;
        Object memberId = redisTemplate.opsForValue().get(tokenKey);
        return memberId != null ? Long.valueOf(memberId.toString()) : null;
    }

    /**
     * 验证 Token 是否有效
     */
    public boolean validateTokenInRedis(String token) {
        String tokenKey = TOKEN_PREFIX + token;
        return redisTemplate.hasKey(tokenKey);
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
     * 根据会员ID删除所有 Token
     */
    public void removeTokenByMemberId(Long memberId) {
        String memberTokenKey = MEMBER_PREFIX + memberId;
        Object token = redisTemplate.opsForValue().get(memberTokenKey);
        if (token != null) {
            String tokenKey = TOKEN_PREFIX + token;
            redisTemplate.delete(tokenKey);
        }
        redisTemplate.delete(memberTokenKey);
    }
}
