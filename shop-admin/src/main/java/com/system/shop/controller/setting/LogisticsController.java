package com.system.shop.controller.setting;

import com.github.pagehelper.PageInfo;
import com.system.shop.common.Result;
import com.system.shop.controller.BaseController;
import com.system.shop.entity.Logistics;
import com.system.shop.bean.search.LogisticsSearch;
import com.system.shop.service.LogisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/logistics")
@RestController
public class LogisticsController extends BaseController {

    @Autowired
    private LogisticsService logisticsService;

    @PostMapping("/list")
    public Result<PageInfo<Logistics>> list(@RequestBody LogisticsSearch bean) {
        return Result.success(logisticsService.selectPage(bean.getPageNum(), bean.getPageSize(), bean.querymap()));
    }

    @PostMapping("/updateStatus")
    public Result<Boolean> updateStatus(@RequestBody Logistics logistics) {
        return Result.success(logisticsService.updateByIdSelective(logistics));
    }


    @GetMapping("/select/{id}")
    public Result<Logistics> selectById(@PathVariable Long id) {
        return Result.success(logisticsService.selectById(id));
    }

    @PostMapping("/saveOrUpdate")
    public Result<Boolean> saveOrUpdate(@RequestBody Logistics logisticsCompany) {
        return Result.success(logisticsService.saveOrUpdate(logisticsCompany));
    }


    @GetMapping("/delete/{id}")
    public Result<Boolean> delete(@PathVariable Long id) {
        return Result.success(logisticsService.deleteById(id));
    }

    @PostMapping("/deleteList")
    public Result<Boolean> deleteList(@RequestBody List<Long> ids) {
        return Result.success(logisticsService.deleteList(ids));
    }

}
