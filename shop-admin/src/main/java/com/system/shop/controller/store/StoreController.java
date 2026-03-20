package com.system.shop.controller.store;

import com.github.pagehelper.PageInfo;
import com.system.shop.common.Result;
import com.system.shop.controller.BaseController;
import com.system.shop.entity.Store;
import com.system.shop.bean.search.StoreSearch;
import com.system.shop.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/store")
@RestController
public class StoreController extends BaseController {

    @Autowired
    private StoreService storeService;


    @PostMapping("/selectPage")
    public Result<PageInfo<Store>> selectPage(@RequestBody StoreSearch search) {
        return Result.success(storeService.selectPage(search.getPageNum(), search.getPageSize(), search.querymap()));
    }

    @GetMapping("/selectListOpen")
    public Result<List<Store>> selectListOpen() {
        return Result.success(storeService.selectList(new StoreSearch(null, Store.STATUS_OPEN).querymap()));
    }

}
