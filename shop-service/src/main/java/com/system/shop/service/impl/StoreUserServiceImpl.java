package com.system.shop.service.impl;

import com.system.shop.base.ServiceImpl;
import com.system.shop.mapper.StoreUserMapper;
import com.system.shop.entity.StoreMenu;
import com.system.shop.entity.StoreUser;
import com.system.shop.service.StoreMenuService;
import com.system.shop.service.StoreUserRoleService;
import com.system.shop.service.StoreUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StoreUserServiceImpl extends ServiceImpl<StoreUserMapper, StoreUser> implements StoreUserService {

    @Autowired
    private StoreUserRoleService storeUserRoleService;
    @Autowired
    private StoreMenuService storeMenuService;

    @Override
    public StoreUser selectByUserName(String userName) {
        return baseMapper.selectByUserName(userName);
    }

    @Override
    public StoreUser select(Long userId) {
        StoreUser storeUser = baseMapper.selectById(userId);
        storeUser.setRoleIds(storeUserRoleService.selectByUserId(userId));
        return storeUser;
    }

    @Override
    public Boolean saveOrUpdate(StoreUser user) {
        StoreUser bean = this.selectById(user.getId());
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if (bean == null) {
            user.setPassword(encoder.encode(user.getPassword()));
            this.insert(user);
        } else {
            if (!user.getPassword().equals(bean.getPassword())) {
                user.setPassword(encoder.encode(user.getPassword()));
            }
            this.updateByIdSelective(user);
        }
        return storeUserRoleService.insertUserRole(user.getId(), user.getRoleIds());
    }

    @Override
    public Boolean delete(Long userId) {
        storeUserRoleService.deleteByUserId(userId);
        return this.deleteById(userId);
    }

    @Override
    public Boolean deleteList(List<Long> userIds) {
        storeUserRoleService.deleteByUserIds(userIds);
        return baseMapper.deleteByIds(userIds);
    }

    @Override
    public List<StoreMenu> selectMenusByUserId(Long userId) {
        return storeMenuService.selectByUserId(userId);
    }
}
