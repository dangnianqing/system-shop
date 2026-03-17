package com.system.shop.service.service;

import com.system.shop.admin.entity.SysUser;

import java.util.Map;

public interface AuthService {
    Map<String, Object> login(String username, String password);
    
    SysUser getCurrentUser();
    
    void logout();
} 