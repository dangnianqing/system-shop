package com.system.shop.service.impl;

import com.system.shop.base.ServiceImpl;
import com.system.shop.mapper.FreightTemplateMapper;
import com.system.shop.entity.FreightTemplate;
import com.system.shop.service.FreightTemplateChildService;
import com.system.shop.service.FreightTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FreightTemplateServiceImpl extends ServiceImpl<FreightTemplateMapper, FreightTemplate> implements FreightTemplateService {

    @Autowired
    private FreightTemplateChildService freightTemplateChildService;

    @Override
    public List<FreightTemplate> selectByStoreId(Long storeId) {
        return baseMapper.selectByStoreId(storeId);
    }

    @Override
    public Boolean saveOrUpdate(FreightTemplate freightTemplate) {
        if (freightTemplate.getId() == null) {
            baseMapper.insert(freightTemplate);
        } else {
            baseMapper.updateByIdSelective(freightTemplate);
        }
        return freightTemplateChildService.insertList(freightTemplate.getId(), freightTemplate.getFreightTemplateChildList());
    }

    @Override
    public Boolean delete(Long id) {
        freightTemplateChildService.deleteByFreightTemplateId(id);
        return baseMapper.deleteById(id);
    }

    @Override
    public List<FreightTemplate> selectList(Long storeId) {
        List<FreightTemplate> freightTemplates = baseMapper.selectByStoreId(storeId);
        freightTemplates.forEach(freightTemplate -> {
            freightTemplate.setFreightTemplateChildList(freightTemplateChildService.selectByFreightTemplateId(freightTemplate.getId()));
        });
        return freightTemplates;
    }

    @Override
    public FreightTemplate selectInfoById(Long id) {
        FreightTemplate freightTemplate = baseMapper.selectById(id);
        freightTemplate.setFreightTemplateChildList(freightTemplateChildService.selectByFreightTemplateId(id));
        return freightTemplate;
    }
}
