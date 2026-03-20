package com.system.shop.service;

import com.system.shop.bean.Token;
import com.system.shop.entity.SysUser;

import java.util.Map;

public interface AuthService {
    Map<String, Object> login(String username, String password);

    Token refreshToken(String refreshToken);

    SysUser getCurrentUser();

    void logout();
} 