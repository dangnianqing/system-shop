package com.example.systemshop.service;

import com.example.systemshop.entity.SysUser;

import java.util.Map;

public interface AuthService {
    Map<String, Object> login(String username, String password);
    
    SysUser getCurrentUser();
    
    void logout();
} 