package com.system.shop.controller.member;


import com.github.pagehelper.PageInfo;
import com.system.shop.common.Result;
import com.system.shop.controller.BaseController;
import com.system.shop.entity.MemberWalletHistory;
import com.system.shop.bean.search.MemberWalletSearch;
import com.system.shop.service.MemberWalletHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/memberWallet")
@RestController
public class MemberWalletController extends BaseController {
    @Autowired
    private MemberWalletHistoryService memberWalletHistoryService;

    @PostMapping("/selectList")
    public Result<PageInfo<MemberWalletHistory>> list(@RequestBody MemberWalletSearch bean) {
        return Result.success(memberWalletHistoryService.selectPage(bean.getPageNum(), bean.getPageSize(), bean.querymap()));
    }

    @PostMapping("/saveWallet")
    public Result<Boolean> saveWallet(@RequestBody MemberWalletHistory memberWalletHistory) {
        return Result.success(memberWalletHistoryService.saveWallet(memberWalletHistory));
    }
}
