package com.system.shop.service;

import com.system.shop.bean.Token;
import com.system.shop.bean.WxLogin;
import me.chanjar.weixin.common.error.WxErrorException;

/**
 * 会员登录服务接口
 */
public interface LoginService {
    
    /**
     * 微信小程序登录
     */
    Token miniAppLogin(WxLogin login) throws WxErrorException;

    /**
     * 手机号密码登录
     */
    Token login(String mobile, String password);

    /**
     * 刷新 Token
     */
    Token refreshToken(String refreshToken);

    /**
     * 登出
     */
    void logout(Long memberId);
}
