package com.system.shop.service.impl;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;

import com.system.shop.bean.Token;
import com.system.shop.bean.WxLogin;
import com.system.shop.common.ResultCode;
import com.system.shop.entity.Member;
import com.system.shop.entity.MemberWx;
import com.system.shop.exception.BusinessException;
import com.system.shop.security.MemberJwtTokenProvider;
import com.system.shop.security.MemberTokenService;
import com.system.shop.service.LoginService;
import com.system.shop.service.MemberService;
import com.system.shop.service.MemberWxService;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 会员登录服务实现
 */
@Service
@Slf4j
public class LoginServiceImpl implements LoginService {
    @Autowired
    private WxMaService wxMaService;
    @Autowired
    private MemberService memberService;
    @Autowired
    private MemberWxService memberWxService;
    @Autowired
    private MemberJwtTokenProvider memberJwtTokenProvider;
    @Autowired
    private MemberTokenService memberTokenService;

    @Override
    public Token miniAppLogin(WxLogin login) throws WxErrorException {
        WxMaJscode2SessionResult wxMaJscode2SessionResult = wxMaService.getUserService().getSessionInfo(login.getCode());
        MemberWx memberWx = memberWxService.selectByMiniAppOpenId(wxMaJscode2SessionResult.getOpenid());
        Member member;
        if (memberWx == null) {
            // 新用户注册
            member = new Member();
            member.setMobile(wxMaJscode2SessionResult.getOpenid());
            member.setPassword(new BCryptPasswordEncoder().encode("123456"));
            member.setUserName(login.getNickName());
            member.setFace(login.getAvatarUrl());
            memberService.insert(member);
            
            memberWx = new MemberWx();
            memberWx.setMiniAppOpenId(wxMaJscode2SessionResult.getOpenid());
            memberWx.setMemberId(member.getId());
            memberWxService.insert(memberWx);
        } else {
            // 已有用户更新信息
            member = memberService.selectById(memberWx.getMemberId());
            member.setUserName(login.getNickName());
            member.setFace(login.getAvatarUrl());
            member.setLoginTime(new Date());
            memberService.updateByIdSelective(member);
        }

        // 生成 Token
        String accessToken = memberJwtTokenProvider.generateToken(member.getId());
        String refreshToken = memberJwtTokenProvider.generateRefreshToken(member.getId());
        
        // 保存 Token 到 Redis
        memberTokenService.saveTokens(member.getId(), accessToken, refreshToken);
        
        log.info("会员微信小程序登录成功: memberId={}", member.getId());
        return new Token(accessToken, refreshToken);
    }

    @Override
    public Token login(String mobile, String password) {
        Member member = memberService.selectByUserName(mobile);
        if (member == null) {
            throw new BusinessException(ResultCode.MEMBER_NOT_EXIST);
        }
        
        // 验证密码
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        if (!passwordEncoder.matches(password, member.getPassword())) {
            throw new BusinessException(ResultCode.MEMBER_NOT_EXIST, "密码错误");
        }
        
        // 更新登录时间
        member.setLoginTime(new Date());
        memberService.updateByIdSelective(member);

        // 生成 Token
        String accessToken = memberJwtTokenProvider.generateToken(member.getId());
        String refreshToken = memberJwtTokenProvider.generateRefreshToken(member.getId());
        
        // 保存 Token 到 Redis
        memberTokenService.saveTokens(member.getId(), accessToken, refreshToken);
        
        log.info("会员登录成功: memberId={}", member.getId());
        return new Token(accessToken, refreshToken);
    }

    @Override
    public Token refreshToken(String refreshToken) {
        log.debug("开始刷新 Token");

        // 验证 refreshToken 格式
        if (!memberJwtTokenProvider.validateToken(refreshToken)) {
            log.error("无效的 refreshToken");
            throw new BusinessException(ResultCode.MEMBER_LOGIN_EXPIRED);
        }

        // 验证是否为 refreshToken 类型
        if (!memberJwtTokenProvider.isRefreshToken(refreshToken)) {
            log.error("Token 不是 refreshToken 类型");
            throw new BusinessException("token.not.refresh");
        }

        // 验证 refreshToken 是否在 Redis 中有效
        if (!memberTokenService.validateRefreshTokenInRedis(refreshToken)) {
            log.error("refreshToken 已过期");
            throw new BusinessException(ResultCode.MEMBER_LOGIN_EXPIRED);
        }

        // 获取会员ID
        Long memberId = memberTokenService.getMemberIdByRefreshToken(refreshToken);
        if (memberId == null) {
            log.error("无法从 refreshToken 获取会员ID");
            throw new BusinessException(ResultCode.MEMBER_LOGIN_EXPIRED);
        }

        // 删除旧的 refreshToken
        memberTokenService.removeRefreshToken(refreshToken);

        // 生成新的 accessToken 和 refreshToken
        String newAccessToken = memberJwtTokenProvider.generateToken(memberId);
        String newRefreshToken = memberJwtTokenProvider.generateRefreshToken(memberId);

        // 保存新的 Token
        memberTokenService.saveTokens(memberId, newAccessToken, newRefreshToken);

        log.debug("Token 刷新成功: memberId={}", memberId);
        return new Token(newAccessToken, newRefreshToken);
    }

    @Override
    public void logout(Long memberId) {
        log.debug("会员登出: memberId={}", memberId);
        memberTokenService.removeTokenByMemberId(memberId);
    }
}
