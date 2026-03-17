package com.system.shop.enumer;


public enum UserObjectEnum {
    /**
     * 角色
     */
    MEMBER("会员"),
    SELLER("商家"),
    MANAGER("管理员");
    private final String name;

    UserObjectEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
