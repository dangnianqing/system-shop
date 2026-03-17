package com.system.shop.service;

import com.system.shop.base.IService;
import com.system.shop.entity.FreightTemplate;

import java.util.List;
public interface FreightTemplateService extends IService<FreightTemplate> {


    List<FreightTemplate> selectByStoreId(Long storeId);

    Boolean saveOrUpdate(FreightTemplate freightTemplate);

    Boolean delete(Long id);

    List<FreightTemplate> selectList(Long storeId);

    FreightTemplate selectInfoById(Long id);
}
