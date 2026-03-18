package com.system.shop.controller.member;


import com.github.pagehelper.PageInfo;
import com.system.shop.bean.search.MemberAddressSearch;
import com.system.shop.common.Result;
import com.system.shop.controller.BaseController;
import com.system.shop.entity.MemberAddress;
import com.system.shop.service.MemberAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/memberAddress")
@RestController
public class MemberAddressController extends BaseController {

    @Autowired
    private MemberAddressService memberAddressService;


    @PostMapping("/selectList")
    public Result<PageInfo<MemberAddress>> selectList(@RequestBody MemberAddressSearch bean) {
        return Result.success(memberAddressService.selectPage(bean.getPageNum(), bean.getPageSize(), bean.querymap()));
    }

    @GetMapping("/selectById/{id}")
    public Result<MemberAddress> selectById(@PathVariable Long id) {
        return Result.success(memberAddressService.selectById(id));
    }


    @PostMapping("/saveOrUpdate")
    public Result<Boolean> saveOrUpdate(@RequestBody MemberAddress bean) {
        return Result.success(memberAddressService.saveOrUpdate(bean));
    }

}
