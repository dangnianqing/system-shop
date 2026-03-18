package com.system.shop.controller.order;


import com.github.pagehelper.PageInfo;
import com.system.shop.bean.order.AfterSaleInfo;
import com.system.shop.common.Result;
import com.system.shop.controller.BaseController;
import com.system.shop.entity.AfterSale;
import com.system.shop.entity.AfterSaleLog;
import com.system.shop.bean.search.AfterSaleSearch;
import com.system.shop.service.AfterSaleItemService;
import com.system.shop.service.AfterSaleLogService;
import com.system.shop.service.AfterSaleService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/afterSale")
@RestController
public class AfterSaleController extends BaseController {

    @Autowired
    private AfterSaleService afterSaleService;
    @Autowired
    private AfterSaleLogService afterSaleLogService;
    @Autowired
    private AfterSaleItemService afterSaleItemService;


    @GetMapping(value = "/selectAfterSaleInfo")
    public Result<AfterSale> selectAfterSaleInfo(String orderCode) {
        return Result.success(afterSaleService.selectAfterSaleInfo(orderCode));
    }

    @PostMapping(value = "/saveAfterSale")
    public Result<Boolean> saveAfterSale(@RequestBody AfterSaleInfo afterSaleInfo, HttpServletRequest request) {
        return Result.success(afterSaleService.saveAfterSale(afterSaleInfo, getOperator(request)));
    }

    @PostMapping("/selectPage")
    public Result<PageInfo<AfterSale>> findPage(@RequestBody AfterSaleSearch afterSaleSearch, HttpServletRequest request) {
        afterSaleSearch.setMemberId(this.getLoginMember(request).getId());
        PageInfo<AfterSale> pageInfo = afterSaleService.selectPage(afterSaleSearch.getPageNumber(), afterSaleSearch.getPageSize(), afterSaleSearch.querymap());
        pageInfo.getList().forEach(afterSale -> {
            afterSale.setAfterSaleItems(afterSaleItemService.selectByAfterSaleCode(afterSale.getAfterSaleCode()));
        });
        return Result.success(pageInfo);
    }
    @GetMapping("/selectAfterSaleCode")
    public Result<AfterSale> selectAfterSaleCode(String afterSaleCode) {
        return Result.success(afterSaleService.selectAfterSaleCode(afterSaleCode));
    }


    @GetMapping("/selectAfterSaleLogs")
    public Result<List<AfterSaleLog>> selectAfterSaleLogs(String afterSaleCode) {
        return Result.success(afterSaleLogService.selectByAfterSaleCode(afterSaleCode));
    }
}
