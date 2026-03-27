package com.system.shop.controller;

import com.system.shop.bean.Token;
import com.system.shop.bean.WxLogin;
import com.system.shop.common.Result;
import com.system.shop.entity.Member;
import com.system.shop.service.LoginService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import me.chanjar.weixin.common.error.WxErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 会员登录控制器
 */
@RestController
@RequestMapping("/auth")
public class LoginController extends BaseController {

    @Autowired
    private LoginService loginService;

    /**
     * 微信小程序登录
     */
    @PostMapping("/wxLogin")
    public Result<Token> wxLogin(@RequestBody WxLogin wxLogin) throws WxErrorException {
        return Result.success(loginService.miniAppLogin(wxLogin));
    }

    /**
     * 手机号密码登录
     */
    @PostMapping("/login")
    public Result<Token> login(@Valid @RequestBody LoginRequest loginRequest) {
        return Result.success(loginService.login(loginRequest.getMobile(), loginRequest.getPassword()));
    }

    /**
     * 登出
     */
    @PostMapping("/logout")
    public Result<Void> logout(HttpServletRequest request) {
        Long memberId = getLoginMemberId(request);
        loginService.logout(memberId);
        return Result.success();
    }

    /**
     * 获取当前登录会员信息
     */
    @GetMapping("/info")
    public Result<Member> getMemberInfo(HttpServletRequest request) {
        Member member = getLoginMember(request);
        return Result.success(member);
    }

    @Data
    public static class LoginRequest {
        @NotBlank(message = "member.mobile.required")
        private String mobile;
        
        @NotBlank(message = "member.password.required")
        private String password;
    }


}
