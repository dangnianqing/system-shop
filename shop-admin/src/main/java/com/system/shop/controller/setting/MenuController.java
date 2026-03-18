package com.system.shop.controller.setting;

import com.system.shop.common.Result;
import com.system.shop.controller.BaseController;
import com.system.shop.entity.SysMenu;
import com.system.shop.service.SysMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author ：zhanghaijun
 * @Date ：Created in  2024/4/16 15:02
 * @Description：
 */
@RequestMapping("/menu")
@RestController
public class MenuController extends BaseController {
    @Autowired
    private SysMenuService sysMenuService;

    @PostMapping("/list")
    public Result<List<SysMenu>> list() {
        return Result.success(sysMenuService.selectListTree());
    }

    @GetMapping("/delete/{id}")
    public Result<Boolean> delete(@PathVariable Long id) {
        return Result.success(sysMenuService.delete(id));
    }
    @GetMapping("/select/{id}")
    public Result<SysMenu> select(@PathVariable Long id) {
        return Result.success(sysMenuService.select(id));
    }


    @PostMapping("/saveOrUpdate")
    public Result<Boolean> saveOrUpdate(@RequestBody SysMenu menu) {
        return Result.success(sysMenuService.saveOrUpdate(menu));
    }

}
