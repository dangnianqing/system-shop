package com.system.shop.service;

import com.system.shop.base.IService;
import com.system.shop.entity.Region;

import java.util.List;


public interface RegionService extends IService<Region> {


    Boolean initData();


    List<Region> selectByParentId(Long parentCode);

    List<Region> selectListTree();

    List<Region> selectCity();
}
