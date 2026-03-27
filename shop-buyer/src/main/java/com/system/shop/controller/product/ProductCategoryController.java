package com.system.shop.controller.product;

import com.system.shop.common.Result;
import com.system.shop.entity.ProductCategory;
import com.system.shop.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author ：zhanghaijun
 * @Date ：Created in  2024/5/7 14:46
 * @Description：
 */
@RequestMapping("/productCategory")
@RestController
public class ProductCategoryController {

    @Autowired
    private ProductCategoryService productCategoryService;

    @GetMapping("/getCategoryList")
    public Result<List<ProductCategory>> getCategoryList() {
        return Result.success(productCategoryService.selectListOneLevel());
    }
    @GetMapping("/getCategoryChildrenList/{parentId}")
    public Result<ProductCategory> getCategoryChildrenList(@PathVariable Long parentId) {
        return Result.success(productCategoryService.selectCategoryChildrenList(parentId));
    }

    @GetMapping("/selectList")
    public Result<List<ProductCategory>> selectList() {
        return Result.success(productCategoryService.selectTreeList());
    }
}
