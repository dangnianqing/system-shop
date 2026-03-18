package com.system.shop.service.impl;

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
            String jwt = tokenProvider.generateToken(authentication);

            // 将 token 存入 Redis
            UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
            tokenService.saveToken(userPrincipal.getId(), jwt);

            Map<String, Object> result = new HashMap<>();
            result.put("token", jwt);
            result.put("user", userService.findByUsername(username));
            logger.debug("User {} logged in successfully", username);
            return result;
        } catch (Exception e) {
            logger.error("Login failed for user: " + username, e);
            throw e;
        }
    }

    @Override
    public SysUser getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            logger.error("No authenticated user found");
            throw new BusinessException("common.unauthorized");
        }
        logger.debug("Getting current user: {}", authentication.getName());
        return userService.findByUsername(authentication.getName());
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