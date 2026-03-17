package com.system.shop.service;


import com.github.pagehelper.PageInfo;
import com.shop.bean.order.AfterSaleInfo;
import com.system.shop.base.IService;
import com.system.shop.bean.Operator;
import com.system.shop.entity.AfterSale;
import com.system.shop.search.AfterSaleSearch;

import java.math.BigDecimal;

public interface AfterSaleService extends IService<AfterSale> {


    AfterSale selectAfterSaleInfo(String orderCode);

    Boolean saveAfterSale(AfterSaleInfo afterSaleInfo, Operator operator);

    PageInfo<AfterSale> findPage(AfterSaleSearch afterSaleSearch);

    AfterSale selectByAfterSaleCode(String afterSaleCode);

    AfterSale selectAfterSaleCode(String afterSaleCode);

    Boolean auditAfterSale(String afterSaleCode, Integer status, BigDecimal applyAfterSalePrice, String storeRemark);

    Boolean refund(AfterSale afterSale);
}
