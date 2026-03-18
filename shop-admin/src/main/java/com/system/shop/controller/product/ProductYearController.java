package com.system.shop.controller.product;

import com.system.shop.common.Result;
import com.system.shop.controller.BaseController;
import com.system.shop.entity.ProductYear;
import com.system.shop.service.ProductYearService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/productYear")
@RestController
public class ProductYearController extends BaseController {
    @Autowired
    private ProductYearService productYearService;

    @GetMapping("/selectList")
    public Result<List<ProductYear>> selectList() {
        return Result.success(productYearService.selectList(null));
    }

    @PostMapping("/saveOrUpdate")
    public Result<Boolean> selectList(@RequestBody ProductYear productYear) {
        return Result.success(productYearService.saveOrUpdate(productYear));
    }
}
