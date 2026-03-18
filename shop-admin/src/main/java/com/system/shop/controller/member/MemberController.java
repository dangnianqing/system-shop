package com.system.shop.controller.member;

import com.github.pagehelper.PageInfo;

import com.system.shop.common.Result;
import com.system.shop.controller.BaseController;
import com.system.shop.entity.Member;
import com.system.shop.bean.search.MemberSearch;
import com.system.shop.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/member")
@RestController
public class MemberController extends BaseController {

    @Autowired
    private MemberService memberService;

    @PostMapping("/list")
    public Result<PageInfo<Member>> list(@RequestBody MemberSearch bean) {
        return Result.success(memberService.selectPage(bean.getPageNum(), bean.getPageSize(), bean.querymap()));
    }

    @PostMapping("/updateStatus")
    public Result<Boolean> updateStatus(@RequestBody Member logisticsCompany) {
        return Result.success(memberService.updateByIdSelective(logisticsCompany));
    }


    @GetMapping("/select/{id}")
    public Result<Member> selectById(@PathVariable Long id) {
        return Result.success(memberService.selectById(id));
    }

}
