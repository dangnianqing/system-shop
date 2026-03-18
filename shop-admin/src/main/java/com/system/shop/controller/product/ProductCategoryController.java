package com.system.shop.controller.product;

import com.system.shop.common.Result;
import com.system.shop.controller.BaseController;
import com.system.shop.entity.ProductCategory;
import com.system.shop.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/productCategory")
@RestController
public class ProductCategoryController extends BaseController {
    @Autowired
    private ProductCategoryService productCategoryService;

    @GetMapping("/selectList")
    public Result<List<ProductCategory>> selectList() {
        return Result.success(productCategoryService.selectTreeList());
    }


}
