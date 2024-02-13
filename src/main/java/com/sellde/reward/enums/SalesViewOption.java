package com.sellde.reward.enums;

public enum SalesViewOption {
    TODAY,
    THIS_WEEK,
    LAST_WEEK,
    THIS_MONTH,
    LAST_MONTH,
    LAST_3_MONTHS;

    public static SalesViewOption defaultOption() {
        return LAST_WEEK;
    }

}
