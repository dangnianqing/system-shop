package com.system.shop.controller.member;


import com.system.shop.common.Result;
import com.system.shop.controller.BaseController;
import com.system.shop.entity.MemberAddress;
import com.system.shop.service.MemberAddressService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

@RequestMapping("/memberAddress")
@RestController
public class MemberAddressController extends BaseController {

    @Autowired
    private MemberAddressService memberAddressService;

    /**
     *
     */
    @PostMapping("/saveOrUpdate")
    public Result<Boolean> saveOrUpdate(@RequestBody MemberAddress memberAddress, HttpServletRequest request) {
        memberAddress.setMemberId(this.getLoginMember(request).getId());
        return Result.success(memberAddressService.saveOrUpdate(memberAddress));
    }


    @GetMapping("/selectList")
    public Result<List<MemberAddress>> list(HttpServletRequest request) {
        return Result.success(memberAddressService.selectList(new HashMap<String, Object>() {{
            put("memberId", getLoginMember(request).getId());
        }}));
    }

    @GetMapping("/selectById/{id}")
    public Result<MemberAddress> selectById(@PathVariable Long id) {
        return Result.success(memberAddressService.selectById(id));
    }


    @GetMapping("/deleteById/{id}")
    public Result<Boolean> delete(@PathVariable Long id, HttpServletRequest request) {
        return Result.success(memberAddressService.delete(id, this.getLoginMember(request).getId()));
    }
}
