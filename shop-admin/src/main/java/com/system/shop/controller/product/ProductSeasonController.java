package com.system.shop.controller.product;

import com.system.shop.common.Result;
import com.system.shop.controller.BaseController;
import com.system.shop.entity.ProductSeason;
import com.system.shop.service.ProductSeasonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/productSeason")
@RestController
public class ProductSeasonController extends BaseController {
    @Autowired
    private ProductSeasonService productSeasonService;

    @GetMapping("/selectList")
    public Result<List<ProductSeason>> selectList() {
        return Result.success(productSeasonService.selectList(null));
    }

    @PostMapping("/saveOrUpdate")
    public Result<Boolean> selectList(@RequestBody ProductSeason productSeason) {
        return Result.success(productSeasonService.saveOrUpdate(productSeason));
    }
}
