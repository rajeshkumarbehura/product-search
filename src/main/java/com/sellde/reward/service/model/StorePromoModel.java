package com.sellde.reward.service.model;

import lombok.Data;

import java.util.List;

@Data
public class StorePromoModel {
    private boolean isStoreShippingFeePromoOn;
    private boolean isStoreSignupDiscountPromoOn;
    private boolean isStoreDiscountCouponPromoOn;

    private String storeShippingFeePromoType;
    private String storeSignupBonusPromoType;
    private String storeDiscountCouponPromoType;

    private List<ShippingFeePromoModel> shippingFeePromoList;
    private List<SignupRewardModel> signupRewardList;
    private List<DiscountCouponModel> discountCouponList;
}
