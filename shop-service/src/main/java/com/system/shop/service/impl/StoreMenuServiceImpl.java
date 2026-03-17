package com.system.shop.service.impl;

import com.system.shop.base.ServiceImpl;
import com.system.shop.mapper.StoreMenuMapper;
import com.system.shop.entity.StoreMenu;
import com.system.shop.service.StoreMenuService;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StoreMenuServiceImpl extends ServiceImpl<StoreMenuMapper, StoreMenu> implements StoreMenuService {


    @Override
    public List<StoreMenu> selectListTree() {
        List<StoreMenu> list = this.selectList(null);
        return list.stream().filter(o -> o.getLevel() == 0).peek(o -> o.setChildren(initChild(o, list)))
                // 收集
                .collect(Collectors.toList());
    }

    @Override
    public Boolean delete(Long id) {
        return this.deleteById(id);
    }

    @Override
    public StoreMenu select(Long id) {
        return this.selectById(id);
    }

    @Override
    public Boolean saveOrUpdate(StoreMenu menu) {
        if (this.selectById(menu.getId()) == null) {
            this.insert(menu);
        } else {
            this.updateByIdSelective(menu);
        }
        return true;
    }

    @Override
    public List<StoreMenu> selectByUserId(Long userId) {
        List<StoreMenu> list = baseMapper.selectByUserId(userId);
        return list.stream().filter(o -> o.getLevel() == 0).peek(o -> o.setChildren(initChild(o, list)))
                // 收集
                .collect(Collectors.toList());
    }

    private List<StoreMenu> initChild(StoreMenu model, List<StoreMenu> menus) {
        return menus.stream().filter(o -> o.getParentId().equals(model.getId()))
                .peek(o -> o.setChildren(initChild(o, menus)))
                .sorted(Comparator.comparing(StoreMenu::getSort))
                .collect(Collectors.toList());
    }
}
