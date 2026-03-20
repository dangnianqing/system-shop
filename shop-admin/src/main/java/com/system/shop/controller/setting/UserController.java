package com.system.shop.controller.setting;

import com.github.pagehelper.PageInfo;
import com.system.shop.common.Result;
import com.system.shop.controller.BaseController;
import com.system.shop.entity.SysUser;
import com.system.shop.bean.search.UserSearch;
import com.system.shop.service.SysUserService;
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
 * @Date ：Created in  2024/4/16 10:46
 * @Description：
 */
@RequestMapping("/user")
@RestController
public class UserController extends BaseController {

    @Autowired
    private SysUserService userService;

    @PostMapping("/list")
    public Result<PageInfo<SysUser>> list(@RequestBody UserSearch userSearch) {
        return Result.success(userService.selectPage(userSearch.getPageNum(), userSearch.getPageSize(), userSearch.querymap()));
    }

    @PostMapping("/updateStatus")
    public Result<Boolean> updateStatus(@RequestBody SysUser sysUser){
        return Result.success(userService.updateByIdSelective(sysUser));
    }


    @GetMapping("/select/{id}")
    public Result<SysUser> select(@PathVariable Long id) {
        return Result.success(userService.select(id));
    }

    @PostMapping("/saveOrUpdate")
    public Result<Boolean> update(@RequestBody SysUser user) {
        return Result.success(userService.saveOrUpdate(user));
    }


    @GetMapping("/delete/{id}")
    public Result<Boolean> deleteList(@PathVariable Long id) {
        return Result.success(userService.delete(id));
    }

    @PostMapping("/deleteList")
    public Result<Boolean> deleteList(@RequestBody List<Long> userIds) {
        return Result.success(userService.deleteList(userIds));
    }
}
