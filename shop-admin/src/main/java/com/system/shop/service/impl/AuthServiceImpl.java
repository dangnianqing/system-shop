package com.system.shop.service.impl;

import com.system.shop.bean.Token;
import com.system.shop.entity.SysUser;
import com.system.shop.exception.BusinessException;
import com.system.shop.security.JwtTokenProvider;
import com.system.shop.security.TokenService;
import com.system.shop.security.UserPrincipal;
import com.system.shop.service.AuthService;
import com.system.shop.service.SysUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthServiceImpl implements AuthService {
    private static final Logger logger = LoggerFactory.getLogger(AuthServiceImpl.class);

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private SysUserService userService;

    @Override
    public Map<String, Object> login(String username, String password) {
        logger.debug("Attempting to login user: {}", username);
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);
            UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

            // 生成 accessToken 和 refreshToken
            String accessToken = tokenProvider.generateToken(authentication);
            String refreshToken = tokenProvider.generateRefreshToken(userPrincipal.getId());

            // 将 token 存入 Redis
            tokenService.saveTokens(userPrincipal.getId(), accessToken, refreshToken);

            Map<String, Object> result = new HashMap<>();
            result.put("token", new Token(accessToken, refreshToken));
            result.put("user", userService.selectByUserName(username));
            logger.debug("User {} logged in successfully", username);
            return result;
        } catch (Exception e) {
            logger.error("Login failed for user: " + username, e);
            throw e;
        }
    }

    @Override
    public Token refreshToken(String refreshToken) {
        logger.debug("Attempting to refresh token");

        // 验证 refreshToken 格式
        if (!tokenProvider.validateToken(refreshToken)) {
            logger.error("Invalid refresh token");
            throw new BusinessException("token.invalid");
        }

        // 验证是否为 refreshToken 类型
        if (!tokenProvider.isRefreshToken(refreshToken)) {
            logger.error("Token is not a refresh token");
            throw new BusinessException("token.not.refresh");
        }

        // 验证 refreshToken 是否在 Redis 中有效
        if (!tokenService.validateRefreshTokenInRedis(refreshToken)) {
            logger.error("Refresh token not found in Redis");
            throw new BusinessException("token.expired");
        }

        // 获取用户ID
        Long userId = tokenService.getUserIdByRefreshToken(refreshToken);
        if (userId == null) {
            logger.error("User ID not found for refresh token");
            throw new BusinessException("token.invalid");
        }

        // 删除旧的 refreshToken
        tokenService.removeRefreshToken(refreshToken);

        // 生成新的 accessToken 和 refreshToken
        String newAccessToken = tokenProvider.generateTokenByUserId(userId);
        String newRefreshToken = tokenProvider.generateRefreshToken(userId);

        // 保存新的 token
        tokenService.saveTokens(userId, newAccessToken, newRefreshToken);

        logger.debug("Token refreshed successfully for user: {}", userId);
        return new Token(newAccessToken, newRefreshToken);
    }

    @Override
    public SysUser getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            logger.error("No authenticated user found");
            throw new BusinessException("common.unauthorized");
        }
        logger.debug("Getting current user: {}", authentication.getName());
        return userService.selectByUserName(authentication.getName());
    }

    @Override
    public void logout() {
        logger.debug("Logging out user");
        // 从 SecurityContext 获取当前用户并删除 Redis 中的 token
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserPrincipal) {
            UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
            tokenService.removeTokenByUserId(userPrincipal.getId());
        }
        SecurityContextHolder.clearContext();
    }
} 