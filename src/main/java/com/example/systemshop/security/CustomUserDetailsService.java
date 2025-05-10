package com.example.systemshop.security;

import com.example.systemshop.entity.SysUser;
import com.example.systemshop.mapper.SysUserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private static final Logger logger = LoggerFactory.getLogger(CustomUserDetailsService.class);

    @Autowired
    private SysUserMapper userMapper;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.debug("Loading user by username: {}", username);
        SysUser user = userMapper.findByUsername(username)
                .orElseThrow(() -> {
                    logger.error("User not found with username: {}", username);
                    return new UsernameNotFoundException("User not found with username: " + username);
                });
        logger.debug("Found user: {}", user);
        return UserPrincipal.create(user);
    }

    @Transactional
    public UserDetails loadUserById(Long id) {
        logger.debug("Loading user by id: {}", id);
        SysUser user = userMapper.findById(id)
                .orElseThrow(() -> {
                    logger.error("User not found with id: {}", id);
                    return new UsernameNotFoundException("User not found with id: " + id);
                });
        logger.debug("Found user: {}", user);
        return UserPrincipal.create(user);
    }
} 