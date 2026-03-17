package com.system.shop.service;

import com.system.shop.base.IService;
import com.system.shop.entity.FreightTemplateChild;

import java.util.List;
public interface FreightTemplateChildService extends IService<FreightTemplateChild> {


    List<FreightTemplateChild> selectByFreightTemplateId(Long freightTemplateId);

    boolean deleteByFreightTemplateId(Long freightTemplateId);

    Boolean insertList(Long freightTemplateId, List<FreightTemplateChild> freightTemplateChildList);
}
