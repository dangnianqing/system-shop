package com.system.shop.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

/**
 * shop-buyer 安全配置
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .cors(cors -> cors.configure(http))
            .csrf(AbstractHttpConfigurer::disable)
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
            .authorizeHttpRequests(auth -> auth
                // 登录接口 - 不需要认证
                .requestMatchers("/auth/wxLogin", "/auth/login","/auth/info").permitAll()
                // 商品分类接口 - 公开访问
                .requestMatchers("/productCategory/**").permitAll()
                .requestMatchers("/product/**").permitAll()
                // 其他请求需要认证（包括 /auth/info、/auth/logout）
                .anyRequest().authenticated()
            );

        return http.build();
    }
}
