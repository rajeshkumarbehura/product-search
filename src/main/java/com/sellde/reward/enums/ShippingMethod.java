package com.sellde.reward.enums;

public enum ShippingMethod {
    SELF_COLLECT,
    DELIVERY,
    SELF_COLLECT_AND_DELIVERY,
    NA;

    public static ShippingMethod getDefault() {
        return DELIVERY;
    }

    public static ShippingMethod[] getAll() {
        return new ShippingMethod[]{DELIVERY, SELF_COLLECT, SELF_COLLECT_AND_DELIVERY};
    }

}
