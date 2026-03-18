package com.system.shop.controller.regin;

import com.system.shop.common.Result;
import com.system.shop.entity.Region;
import com.system.shop.service.RegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RequestMapping("/region")
@RestController
public class RegionController {

    @Autowired
    private RegionService regionService;


    @GetMapping("/init")
    public Result<Boolean> init() {
        return Result.success(regionService.initData());
    }



    @GetMapping("/select")
    public Result<List<Region>> selectByCode(Long parentId) {
        return Result.success(regionService.selectByParentId(parentId));
    }

    @GetMapping("/selectList")
    public Result<List<Region>> selectList() {
        return Result.success(regionService.selectListTree());
    }
}
