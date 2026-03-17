package com.system.shop.service;


import com.system.shop.base.IService;
import com.system.shop.entity.AfterSaleLog;

import java.util.List;

public interface AfterSaleLogService extends IService<AfterSaleLog> {


    List<AfterSaleLog> selectByAfterSaleCode(String afterSaleCode);

}
