package com.system.shop.controller;

import com.system.shop.bean.Token;
import com.system.shop.bean.WxLogin;
import com.system.shop.common.Result;
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
     * 刷新 Token
     */
    @PostMapping("/refresh")
    public Result<Token> refreshToken(@Valid @RequestBody RefreshTokenRequest request) {
        return Result.success(loginService.refreshToken(request.getRefreshToken()));
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
    public Result<MemberInfo> getMemberInfo(HttpServletRequest request) {

        var member = getLoginMember(request);
        MemberInfo info = new MemberInfo();
        info.setId(member.getId());
        info.setUserName(member.getUserName());
        info.setMobile(member.getMobile());
        info.setFace(member.getFace());
        info.setIntegral(member.getIntegral());
        info.setWallet(member.getWallet());
        return Result.success(info);
    }

    @Data
    public static class LoginRequest {
        @NotBlank(message = "member.mobile.required")
        private String mobile;
        
        @NotBlank(message = "member.password.required")
        private String password;
    }

    @Data
    public static class RefreshTokenRequest {
        @NotBlank(message = "refresh.token.required")
        private String refreshToken;
    }

    @Data
    public static class MemberInfo {
        private Long id;
        private String userName;
        private String mobile;
        private String face;
        private Integer integral;
        private java.math.BigDecimal wallet;
    }
}
