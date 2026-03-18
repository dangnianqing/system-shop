package com.system.shop.controller.order;

import com.system.shop.common.Result;
import com.system.shop.controller.BaseController;
import com.system.shop.entity.AfterSaleReason;
import com.system.shop.service.AfterSaleReasonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;


@RequestMapping("/afterSaleReason")
@RestController
public class AfterSaleReasonController extends BaseController {


    @Autowired
    private AfterSaleReasonService afterSaleReasonService;


    @GetMapping(value = "/init")
    public Result<Boolean> selectAfterSaleInfo() {
        return Result.success(afterSaleReasonService.init());
    }

    @GetMapping(value = "/selectList")
    public Result<List<AfterSaleReason>> selectList(String serviceType) {
        return Result.success(afterSaleReasonService.selectList(new HashMap<String, Object>() {{
            put("serviceType", serviceType);
        }}));
    }
}
