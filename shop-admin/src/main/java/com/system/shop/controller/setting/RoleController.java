package com.system.shop.controller.setting;

import com.github.pagehelper.PageInfo;
import com.system.shop.common.Result;
import com.system.shop.controller.BaseController;
import com.system.shop.entity.SysRole;
import com.system.shop.bean.search.RoleSearch;
import com.system.shop.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @Author ：zhanghaijun
 * @Date ：Created in  2024/4/16 14:45
 * @Description：
 */
@RequestMapping("/role")
@RestController
public class RoleController extends BaseController {
    @Autowired
    private SysRoleService sysRoleService;

    @PostMapping("/list")
    public Result<PageInfo<SysRole>> list(@RequestBody RoleSearch userSearch) {
        return Result.success(sysRoleService.selectPage(userSearch.getPageNumber(), userSearch.getPageSize(), userSearch.querymap()));
    }

    @GetMapping("/select/{id}")
    public Result<SysRole> select(@PathVariable Long id) {
        return Result.success(sysRoleService.selectById(id));
    }

    @PostMapping("/saveOrUpdate")
    public Result<Boolean> saveOrUpdate(@RequestBody SysRole role) {
        return Result.success(sysRoleService.saveOrUpdate(role));
    }

    @GetMapping("/delete/{id}")
    public Result<Boolean> delete(@PathVariable Long id) {
        return Result.success(sysRoleService.delete(id));
    }

    @PostMapping("/deleteList")
    public Result<Boolean> deleteList(@RequestBody List<Long> roleIds) {
        return Result.success(sysRoleService.deleteList(roleIds));
    }


    @PostMapping("/selectList")
    public Result<List<SysRole>> selectList() {
        return Result.success(sysRoleService.selectList(null));
    }

    @GetMapping("/selectMenu/{roleId}")
    public Result<Map<String ,Object>> selectMenu(@PathVariable Long roleId) {
        return Result.success(sysRoleService.selectMenuList(roleId));
    }


    @PostMapping("/saveRoleMenu")
    public Result<Boolean> saveRoleMenu(@RequestBody SysRole role) {
        return Result.success(sysRoleService.saveRoleMenu(role));
    }
}
