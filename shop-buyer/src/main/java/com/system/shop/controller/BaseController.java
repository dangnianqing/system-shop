package com.system.shop.controller;

import com.system.shop.bean.Operator;
import com.system.shop.common.ResultCode;
import com.system.shop.entity.Member;
import com.system.shop.exception.BusinessException;
import com.system.shop.security.MemberJwtTokenProvider;
import com.system.shop.security.MemberTokenService;
import com.system.shop.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RestController;

/**
 * 基础控制器 - 提供获取当前登录会员的通用方法
 */
@RestController
public class BaseController {

    @Autowired
    private MemberTokenService memberTokenService;

    @Autowired
    private MemberJwtTokenProvider memberJwtTokenProvider;

    @Autowired
    private MemberService memberService;

    /**
     * 从 HttpServletRequest 获取当前登录会员
     * @param request HTTP 请求
     * @return 当前登录会员
     */
    protected Member getLoginMember(HttpServletRequest request) {
        String token = getTokenFromRequest(request);
        if (!StringUtils.hasText(token)) {
            throw new BusinessException(ResultCode.MEMBER_NOT_LOGIN);
        }
        return getMemberByToken(token);
    }

    /**
     * 从 HttpServletRequest 获取当前登录会员ID
     * @param request HTTP 请求
     * @return 当前登录会员ID
     */
    protected Long getLoginMemberId(HttpServletRequest request) {
        String token = getTokenFromRequest(request);
        if (!StringUtils.hasText(token)) {
            throw new BusinessException(ResultCode.MEMBER_NOT_LOGIN);
        }
        return getMemberIdByToken(token);
    }

    /**
     * 从 Token 获取当前登录会员
     * @param token JWT token
     * @return 当前登录会员
     */
    protected Member getMemberByToken(String token) {
        Long memberId = getMemberIdByToken(token);
        Member member = memberService.selectById(memberId);
        if (member == null) {
            throw new BusinessException(ResultCode.MEMBER_NOT_EXIST);
        }
        return member;
    }

    /**
     * 从 Token 获取当前登录会员ID
     * @param token JWT token
     * @return 当前登录会员ID
     */
    protected Long getMemberIdByToken(String token) {
        // 先从 Redis 获取
        Long memberId = memberTokenService.getMemberIdByToken(token);
        if (memberId != null) {
            return memberId;
        }
        // Redis 中不存在，从 JWT 解析
        if (memberJwtTokenProvider.validateToken(token) && memberJwtTokenProvider.isMemberToken(token)) {
            return memberJwtTokenProvider.getMemberIdFromJWT(token);
        }
        throw new BusinessException(ResultCode.MEMBER_LOGIN_EXPIRED);
    }

    /**
     * 从请求头获取 Token
     * @param request HTTP 请求
     * @return JWT token
     */
    protected String getTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    /**
     * 获取操作者信息
     * @param request HTTP 请求
     * @return 操作者信息
     */
    public Operator getOperator(HttpServletRequest request) {
        Member member = getLoginMember(request);
        return new Operator(member.getId(), member.getUserName(), "会员");
    }
}
