package com.system.shop.controller.product;

import com.system.shop.common.Result;
import com.system.shop.controller.BaseController;
import com.system.shop.entity.ProductSpec;
import com.system.shop.service.ProductSpecService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/productSpec")
@RestController
public class ProductSpecController extends BaseController {
    @Autowired
    private ProductSpecService productSpecService;

    @PostMapping("/saveOrUpdate")
    public Result<Boolean> selectList(@RequestBody ProductSpec productSpec) {
        return Result.success(productSpecService.saveOrUpdate(productSpec));
    }
}
