package com.system.shop.controller.setting;

import com.system.shop.common.Result;
import com.system.shop.controller.BaseController;
import com.system.shop.entity.SysSetting;
import com.system.shop.service.SysSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/sysSetting")
@RestController
public class SysSettingController extends BaseController {

    @Autowired
    private SysSettingService sysSettingService;

    @PostMapping("/saveOrUpdate")
    public Result<Boolean> saveOrUpdate(@RequestBody SysSetting sysSetting) {
        return Result.success(sysSettingService.saveOrUpdate(sysSetting));
    }

    @GetMapping("/selectList")
    public Result<List<SysSetting>> selectList() {
        return Result.success(sysSettingService.selectList(null));
    }
}
