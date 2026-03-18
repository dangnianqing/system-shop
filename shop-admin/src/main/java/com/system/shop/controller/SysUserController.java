package com.system.shop.controller;

import com.system.shop.common.Result;
import com.system.shop.entity.SysUser;
import com.system.shop.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@CrossOrigin
public class SysUserController {

    @Autowired
    private SysUserService userService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Result<List<SysUser>> getAllUsers() {
        return Result.success(userService.findAll());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public Result<SysUser> getUserById(@PathVariable Long id) {
        return Result.success(userService.selectById(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Result<SysUser> createUser(@Valid @RequestBody SysUser user) {
        return Result.success(userService.create(user));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public Result<SysUser> updateUser(@PathVariable Long id, @Valid @RequestBody SysUser user) {
        user.setId(id);
        return Result.success(userService.update(user));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public Result<Void> deleteUser(@PathVariable Long id) {
        userService.delete(id);
        return Result.success();
    }

    @PostMapping("/{id}/roles")
    @PreAuthorize("hasRole('ADMIN')")
    public Result<Void> assignRoles(@PathVariable Long id, @RequestBody List<Long> roleIds) {
        userService.assignRoles(id, roleIds);
        return Result.success();
    }
} 