package com.system.shop.security;

import com.system.shop.entity.Member;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

/**
 * 会员认证主体
 */
@Getter
public class MemberPrincipal implements UserDetails {
    private final Long id;
    private final String username;
    private final String password;
    private final String mobile;
    private final String face;
    private final Collection<? extends GrantedAuthority> authorities;

    public MemberPrincipal(Long id, String username, String password, String mobile, String face) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.mobile = mobile;
        this.face = face;
        this.authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_MEMBER"));
    }

    public static MemberPrincipal create(Member member) {
        return new MemberPrincipal(
                member.getId(),
                member.getUserName(),
                member.getPassword(),
                member.getMobile(),
                member.getFace()
        );
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
