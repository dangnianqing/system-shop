package com.system.shop.controller;

import com.system.shop.entity.SysUser;
import com.system.shop.exception.BusinessException;
import com.system.shop.security.JwtTokenProvider;
import com.system.shop.security.TokenService;
import com.system.shop.service.SysUserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RestController;

/**
 * 基础控制器 - 提供获取当前登录用户的通用方法
 */
@RestController
public class BaseController {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private SysUserService userService;

    /**
     * 从 HttpServletRequest 获取当前登录用户
     * @param request HTTP 请求
     * @return 当前登录用户
     */
    protected SysUser getCurrentUser(HttpServletRequest request) {
        String token = getTokenFromRequest(request);
        if (!StringUtils.hasText(token)) {
            throw new BusinessException("common.unauthorized");
        }
        return getCurrentUserByToken(token);
    }

    /**
     * 从 HttpServletRequest 获取当前登录用户ID
     * @param request HTTP 请求
     * @return 当前登录用户ID
     */
    protected Long getCurrentUserId(HttpServletRequest request) {
        String token = getTokenFromRequest(request);
        if (!StringUtils.hasText(token)) {
            throw new BusinessException("common.unauthorized");
        }
        return getCurrentUserIdByToken(token);
    }

    /**
     * 从 Token 获取当前登录用户
     * @param token JWT token
     * @return 当前登录用户
     */
    protected SysUser getCurrentUserByToken(String token) {
        Long userId = getCurrentUserIdByToken(token);
        return userService.selectById(userId);
    }

    /**
     * 从 Token 获取当前登录用户ID
     * @param token JWT token
     * @return 当前登录用户ID
     */
    protected Long getCurrentUserIdByToken(String token) {
        // 先从 Redis 获取
        Long userId = tokenService.getUserIdByToken(token);
        if (userId != null) {
            return userId;
        }
        // Redis 中不存在，从 JWT 解析
        if (tokenProvider.validateToken(token)) {
            return tokenProvider.getUserIdFromJWT(token);
        }
        throw new BusinessException("common.unauthorized");
    }

    /**
     * 从请求头获取 Token
     * @param request HTTP 请求
     * @return JWT token
     */
    protected String getTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    /**
     * 从 SecurityContext 获取当前登录用户（适用于已通过 Spring Security 认证的请求）
     * @return 当前登录用户
     */
    protected SysUser getCurrentUserFromSecurityContext() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new BusinessException("common.unauthorized");
        }
        return userService.findByUsername(authentication.getName());
    }
}
