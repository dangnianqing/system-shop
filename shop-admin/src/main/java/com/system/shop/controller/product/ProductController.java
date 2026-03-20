package com.system.shop.controller.product;

import com.github.pagehelper.PageInfo;
import com.system.shop.common.Result;
import com.system.shop.controller.BaseController;
import com.system.shop.entity.Product;
import com.system.shop.bean.search.ProductSearch;
import com.system.shop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author ：zhanghaijun
 * @Date ：Created in  2024/4/22 15:35
 * @Description：
 */

@RequestMapping("/product")
@RestController
public class ProductController extends BaseController {

    @Autowired
    private ProductService productService;

    @PostMapping("/selectPage")
    public Result<PageInfo<Product>> selectPage(@RequestBody ProductSearch productSearch) {
        return Result.success(productService.selectPage(productSearch.getPageNum(), productSearch.getPageSize(), productSearch.querymap()));
    }

    @GetMapping("/selectProduct/{id}")
    public Result<Product> selectProduct(@PathVariable Long id) {
        return Result.success(productService.selectProductById(id));
    }


    @PostMapping("/updateUpDown")
    public Result<Boolean> updateUpDown(@RequestParam("productIds") List<Long> productIds, @RequestParam("status") Integer status) {
        return Result.success(productService.updateUpDown(productIds, status));
    }


    @PostMapping("/reviewProduct")
    public Result<Boolean> reviewProduct(@RequestParam("productId") Long productId, @RequestParam("auditStatus") Integer auditStatus) {
        return Result.success(productService.updateReviewProduct(productId, auditStatus));
    }


}
