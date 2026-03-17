package com.system.shop.service;


import com.system.shop.base.IService;
import com.system.shop.entity.AfterSaleItem;

import java.util.List;

public interface AfterSaleItemService extends IService<AfterSaleItem> {

    List<AfterSaleItem> selectByAfterSaleCode(String afterSaleCode);
}
