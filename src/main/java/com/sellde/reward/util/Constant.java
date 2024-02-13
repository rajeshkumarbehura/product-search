package com.sellde.reward.util;


public class Constant {
    public static final String ACTIVE_PROFILE = "spring.profiles.active";

    public static final String ENV_LOCAL = "local";
    public static final String ENV_LOCAL_DEV = "localdev";
    public static final String ENV_TEST = "test";
    public static final String ENV_PROD = "prod";

    public static final String STAFF = "staff";
    public static final int DEFAULT_PAGE_SIZE = 100;
    public static final String DEFAULT_SORT_ORDER = "DESC";
    public static final String DESC_SORT = "DESC";
    public static final String ASC_SORT = "ASC";
    public static final int CART_DESC_MAX_LENGTH = 500;
    public static final String ISO_UTC_DATE_TIME_FORMAT = "YYYY-MM-DDTHH:mm:ss.sssZ";

    public static final Long SYSTEM_PHONE_NO = 999999999L;
    public static String SHIPPING_PROMO_CONFIG = "isStoreShippingFeePromoOn";
    public static String SIGNUP_PROMO_CONFIG = "isStoreSignupDiscountPromoOn";
    public static String DISCOUNT_COUPON_CONFIG = "isStoreDiscountCouponPromoOn";
    public static String NOT_AVIALBLE = "NA";

    private Constant() {
    }
}
