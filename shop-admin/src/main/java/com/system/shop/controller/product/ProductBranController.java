package com.system.shop.controller.product;

import com.system.shop.common.Result;
import com.system.shop.controller.BaseController;
import com.system.shop.entity.ProductBrand;
import com.system.shop.service.ProductBrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/productBrand")
@RestController
public class ProductBranController extends BaseController {
    @Autowired
    private ProductBrandService productBrandService;
    @GetMapping("/selectList")
    public Result<List<ProductBrand>> selectList() {
        return Result.success(productBrandService.selectList(null));
    }
}
