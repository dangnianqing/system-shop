package com.system.shop.controller.member;

import com.system.shop.common.Result;
import com.system.shop.controller.BaseController;
import com.system.shop.entity.Member;
import com.system.shop.entity.MemberReceipt;
import com.system.shop.service.MemberReceiptService;
import com.system.shop.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/member")
@RestController
public class MemberController extends BaseController {

    @Autowired
    private MemberService memberService;


    @Autowired
    private MemberReceiptService memberReceiptService;

    @PostMapping("/update")
    public Result<Boolean> update(@RequestBody Member member, HttpServletRequest request) {
        return Result.success(memberService.update(member, this.getLoginMember(request).getId()));
    }


    @GetMapping("/info")
    public Result<Member> info(HttpServletRequest request) {
        return Result.success(memberService.selectById(this.getLoginMember(request).getId()));
    }


    @PostMapping("/updateReceipt")
    public Result<Long> update(@RequestBody MemberReceipt memberReceipt, HttpServletRequest request) {
        return Result.success(memberReceiptService.update(memberReceipt, this.getLoginMember(request).getId()));
    }


    @PostMapping("/receipt/selectByMemberId")
    public Result<MemberReceipt> selectByMemberId(HttpServletRequest request) {
        return Result.success(memberReceiptService.selectByMemberId(this.getLoginMember(request).getId()));
    }


}
