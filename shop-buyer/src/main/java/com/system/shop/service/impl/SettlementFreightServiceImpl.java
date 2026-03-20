package com.system.shop.service.impl;

import com.system.shop.bean.cart.ProductSettlement;
import com.system.shop.bean.cart.ShoppSettlement;
import com.system.shop.bean.cart.StoreSettlement;
import com.system.shop.enumer.FreightTemplateEnum;
import com.system.shop.enumer.SettlementType;
import com.system.shop.entity.FreightTemplate;
import com.system.shop.entity.FreightTemplateChild;
import com.system.shop.entity.MemberAddress;
import com.system.shop.service.FreightTemplateService;
import com.system.shop.service.MemberAddressService;
import com.system.shop.service.SettlementService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class SettlementFreightServiceImpl implements SettlementService {
    @Autowired
    private MemberAddressService memberAddressService;
    @Autowired
    private FreightTemplateService freightTemplateService;

    @Override
    public SettlementType step() {
        return SettlementType.FREIGHT;
    }

    @Override
    public void compute(ShoppSettlement shoppSettlement) {
        shoppSettlement.setMemberAddress(shoppSettlement.getAddressId() == null ? memberAddressService.selectByDefaultMemberId(shoppSettlement.getMemberId()) : memberAddressService.selectById(shoppSettlement.getAddressId()));
        shoppSettlement.setAddressId(shoppSettlement.getMemberAddress() == null ? null : shoppSettlement.getMemberAddress().getId());
        for (StoreSettlement storeCartItem : shoppSettlement.getStoreSettlements()) {
            if (shoppSettlement.getMemberAddress() != null) {
                BigDecimal storeFreightPrice = BigDecimal.ZERO;
                MemberAddress memberAddress = shoppSettlement.getMemberAddress();
                Map<Long, List<ProductSettlement>> freightTemMap = storeCartItem.getProductSettlements().stream().collect(Collectors.groupingBy(ProductSettlement::getFreightTemplateId));
                for (Map.Entry<Long, List<ProductSettlement>> entry : freightTemMap.entrySet()) {
                    FreightTemplate freightTemplate = freightTemplateService.selectInfoById(entry.getKey());
                    //查询不到运费模板  或者运费模板的是包邮
                    if (freightTemplate == null || CollectionUtils.isEmpty(freightTemplate.getFreightTemplateChildList()) || freightTemplate.getPricingMethod().equals(FreightTemplateEnum.FREE.name())) {
                        break;
                    }
                    String addressId = memberAddress.getPathId().split(",")[1];
                    List<FreightTemplateChild> childList = freightTemplate.getFreightTemplateChildList().stream().filter(freightTemplateChild -> freightTemplateChild.getRegionId().contains(addressId)).toList();
                    if (CollectionUtils.isEmpty(childList)) {
                        log.info("购物车中包含不支持配送的商品，请重新选择收货地址，或者重新选择商品 包含商品有-{}", entry.getValue().stream().map(ProductSettlement::getProductName).collect(Collectors.joining(",")));
                        shoppSettlement.getNotDeliveryProducts().addAll(entry.getValue());
                        return;
                    }
                    FreightTemplateChild child = childList.get(0);
                    //总重量或者总件数
                    int totalNum = entry.getValue().stream().mapToInt(ProductSettlement::getNum).sum();
                    BigDecimal totalWeight = BigDecimal.ZERO;
                    for (ProductSettlement productSettlement : entry.getValue()) {
                        totalWeight = totalWeight.add(productSettlement.getWeight().multiply(new BigDecimal(productSettlement.getNum())));
                    }
                    //运费
                    storeFreightPrice = storeFreightPrice.add(computeFreightPrice(totalNum, totalWeight, freightTemplate, child));
                }
                storeCartItem.setFreightPrice(storeFreightPrice);
            }
            shoppSettlement.setFreightPrice(shoppSettlement.getFreightPrice() == null ? storeCartItem.getFreightPrice() : shoppSettlement.getFreightPrice().add(storeCartItem.getFreightPrice()));

        }
    }


    private BigDecimal computeFreightPrice(int totalNum, BigDecimal totalWeight, FreightTemplate freightTemplate, FreightTemplateChild freightTemplateChild) {
        BigDecimal freightPrice = freightTemplateChild.getFirstPrice();
        if (FreightTemplateEnum.NUM.name().equals(freightTemplate.getPricingMethod())) {
            if (freightTemplateChild.getFirstNum().compareTo(new BigDecimal(totalNum)) >= 0) {
                return freightPrice.multiply(new BigDecimal(totalNum));
            } else {
                return freightTemplateChild.getContinuedPrice().multiply(new BigDecimal(totalNum));
            }
        } else {
            //不满首重 / 首件
            if (freightTemplateChild.getFirstNum().compareTo(totalWeight) >= 0) {
                return freightPrice;
            }
            //如果续重/续件，费用不为空，则返回
            if (freightTemplateChild.getContinuedNum().compareTo(BigDecimal.ZERO) == 0 || freightTemplateChild.getContinuedPrice().compareTo(BigDecimal.ZERO) == 0) {
                return freightPrice;
            }
            //计算 续重 / 续件 价格
            BigDecimal continuedNum = totalWeight.subtract(freightTemplateChild.getFirstNum()).setScale(0, RoundingMode.HALF_UP).divide(freightTemplateChild.getContinuedNum(), 0, RoundingMode.HALF_UP);
            return freightPrice.add(freightTemplateChild.getContinuedPrice().multiply(continuedNum));
        }
    }
}
