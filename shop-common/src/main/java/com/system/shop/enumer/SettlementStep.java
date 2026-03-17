package com.system.shop.enumer;

import java.util.ArrayList;
import java.util.List;

public class SettlementStep {


    public static List<SettlementType> settlementCart() {
        List<SettlementType> list = new ArrayList<>();
        list.add(SettlementType.INIT);
        list.add(SettlementType.ACTIVITY);
        list.add(SettlementType.COUPON);
        list.add(SettlementType.SUM);
        return list;
    }


    public static List<SettlementType> settlementOrder() {
        List<SettlementType> list = new ArrayList<>();
        list.add(SettlementType.INIT);
        list.add(SettlementType.ACTIVITY);
        list.add(SettlementType.COUPON);
        list.add(SettlementType.FREIGHT);
        list.add(SettlementType.SUM);
        return list;
    }
}
