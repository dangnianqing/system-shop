package com.system.shop.controller;

import com.system.shop.common.Result;
import com.system.shop.entity.SysRole;
import com.system.shop.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/roles")
@CrossOrigin
public class SysRoleController {

    @Autowired
    private SysRoleService roleService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Result<List<SysRole>> getAllRoles() {
        return Result.success(roleService.findAll());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public Result<SysRole> getRoleById(@PathVariable Long id) {
        return Result.success(roleService.selectById(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Result<SysRole> createRole(@Valid @RequestBody SysRole role) {
        return Result.success(roleService.create(role));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public Result<SysRole> updateRole(@PathVariable Long id, @Valid @RequestBody SysRole role) {
        role.setId(id);
        return Result.success(roleService.update(role));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public Result<Void> deleteRole(@PathVariable Long id) {
        roleService.delete(id);
        return Result.success();
    }

    @PostMapping("/{id}/menus")
    @PreAuthorize("hasRole('ADMIN')")
    public Result<Void> assignMenus(@PathVariable Long id, @RequestBody List<Long> menuIds) {
        roleService.assignMenus(id, menuIds);
        return Result.success();
    }
} 