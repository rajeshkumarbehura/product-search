package com.sellde.reward.enums;

public enum CustomerMonthlyBadge {

    NA(0),
    BRONZE(2000000),
    SILVER(5000000);

    /* NA, BRONZE, SILVER (0,1,2) */
    public static final int badgeLevel = 2;

    private static final int NA_VALUE = 0;
    private static final int BRONZE_VALUE = 1;
    private static final int SILVER_VALUE = 2;


    public final long value;

    CustomerMonthlyBadge(long value) {
        this.value = value;
    }

    public static int getIndex(CustomerMonthlyBadge badge) {
        return switch (badge) {
            case BRONZE -> BRONZE_VALUE;
            case SILVER -> SILVER_VALUE;
            default -> NA_VALUE;
        };
    }

    public static int compare(CustomerMonthlyBadge previousBadge,
                              CustomerMonthlyBadge currentBadge) {
        var previousIndex = getIndex(previousBadge);
        var currentIndex = getIndex(currentBadge);
        return previousIndex - currentIndex;
    }

    public static CustomerMonthlyBadge getValueByIndex(int index) {
        return switch (index) {
            case BRONZE_VALUE -> BRONZE;
            case SILVER_VALUE -> SILVER;
            default -> NA;
        };
    }

    public static CustomerMonthlyBadge getValueByMinusIndex(CustomerMonthlyBadge badge, int minusIndex) {
        var badgeIndex = getIndex(badge) - minusIndex;
        return switch (badgeIndex) {
            case BRONZE_VALUE -> BRONZE;
            case SILVER_VALUE -> SILVER;
            default -> NA;
        };
    }

}
