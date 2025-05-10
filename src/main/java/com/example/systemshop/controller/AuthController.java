package com.example.systemshop.controller;

import com.example.systemshop.common.Result;
import com.example.systemshop.entity.SysUser;
import com.example.systemshop.payload.LoginRequest;
import com.example.systemshop.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public Result<Map<String, Object>> login(@Valid @RequestBody LoginRequest loginRequest) {
        return Result.success(authService.login(loginRequest.getUsername(), loginRequest.getPassword()));
    }

    @GetMapping("/info")
    public Result<SysUser> getUserInfo() {
        return Result.success(authService.getCurrentUser());
    }

    @PostMapping("/logout")
    public Result<Void> logout() {
        authService.logout();
        return Result.success();
    }
} 