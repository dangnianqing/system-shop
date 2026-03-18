package com.system.shop.controller.member;


import com.github.pagehelper.PageInfo;
import com.system.shop.common.Result;
import com.system.shop.controller.BaseController;
import com.system.shop.entity.MemberIntegralHistory;
import com.system.shop.bean.search.MemberIntegralSearch;
import com.system.shop.service.MemberIntegralHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/memberIntegral")
@RestController
public class MemberIntegralController extends BaseController {
    @Autowired
    private MemberIntegralHistoryService memberIntegralHistoryService;

    @PostMapping("/selectList")
    public Result<PageInfo<MemberIntegralHistory>> list(@RequestBody MemberIntegralSearch bean) {
        return Result.success(memberIntegralHistoryService.selectPage(bean.getPageNum(), bean.getPageSize(), bean.querymap()));
    }

    @PostMapping("/saveIntegral")
    public Result<Boolean> saveIntegral(@RequestBody MemberIntegralHistory memberIntegralHistory) {
        return Result.success(memberIntegralHistoryService.saveIntegral(memberIntegralHistory));
    }
}
