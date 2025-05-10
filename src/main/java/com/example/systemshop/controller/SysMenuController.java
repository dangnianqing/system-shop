package com.example.systemshop.controller;

import com.example.systemshop.common.Result;
import com.example.systemshop.entity.SysMenu;
import com.example.systemshop.service.SysMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/menus")
@CrossOrigin
public class SysMenuController {

    @Autowired
    private SysMenuService menuService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Result<List<SysMenu>> getAllMenus() {
        return Result.success(menuService.findAll());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public Result<SysMenu> getMenuById(@PathVariable Long id) {
        return Result.success(menuService.findById(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Result<SysMenu> createMenu(@Valid @RequestBody SysMenu menu) {
        return Result.success(menuService.create(menu));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public Result<SysMenu> updateMenu(@PathVariable Long id, @Valid @RequestBody SysMenu menu) {
        menu.setId(id);
        return Result.success(menuService.update(menu));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public Result<Void> deleteMenu(@PathVariable Long id) {
        menuService.delete(id);
        return Result.success();
    }
} 