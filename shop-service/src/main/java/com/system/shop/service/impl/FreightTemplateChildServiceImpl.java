package com.system.shop.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.system.shop.base.ServiceImpl;
import com.system.shop.mapper.FreightTemplateChildMapper;
import com.system.shop.entity.FreightTemplateChild;
import com.system.shop.service.FreightTemplateChildService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FreightTemplateChildServiceImpl extends ServiceImpl<FreightTemplateChildMapper, FreightTemplateChild> implements FreightTemplateChildService {


    @Override
    public List<FreightTemplateChild> selectByFreightTemplateId(Long freightTemplateId) {
        return baseMapper.selectByFreightTemplateId(freightTemplateId);
    }

    @Override
    public boolean deleteByFreightTemplateId(Long freightTemplateId) {
        return baseMapper.deleteByFreightTemplateId(freightTemplateId);
    }

    @Override
    public Boolean insertList(Long freightTemplateId, List<FreightTemplateChild> freightTemplateChildList) {
        baseMapper.deleteByFreightTemplateId(freightTemplateId);
        if (CollectionUtil.isNotEmpty(freightTemplateChildList)) {
            freightTemplateChildList.forEach(item -> {
                item.setFreightTemplateId(freightTemplateId);
            });
        }
        return batchInsert(freightTemplateChildList);
    }
}
