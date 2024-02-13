package com.sellde.reward.service.biz;

import com.sellde.reward.service.DiscountCouponService;
import com.sellde.reward.service.SettingService;
import com.sellde.reward.service.ShippingPromoService;
import com.sellde.reward.service.SignupRewardService;
import com.sellde.reward.service.model.SettingModel;
import com.sellde.reward.service.model.StorePromoModel;
import com.sellde.reward.util.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@Transactional
public class StorePromoBizService {

    @Autowired
    private SettingService settingService;

    @Autowired
    private ShippingPromoService shippingPromoService;

    @Autowired
    private SignupRewardService signupRewardService;

    @Autowired
    private DiscountCouponService discountCouponService;

    @Transactional(readOnly = true)
    public Mono<StorePromoModel> findPromos() {
        return shippingPromoService.getAllActivePromos().collectList()
                .zipWith(settingService.getStorePromoList().collectList())
                .map(tuple -> {
                    var resultModel = new StorePromoModel();
                    resultModel.setShippingFeePromoList(tuple.getT1());
                    extractFromSettingList(resultModel, tuple.getT2());
                    return resultModel;
                })
                .zipWith(signupRewardService.getAllByEnableStatus().collectList())
                .map(tuple -> {
                    var resultModel = tuple.getT1();
                    resultModel.setSignupRewardList(tuple.getT2());
                    return resultModel;
                })
                .zipWith(discountCouponService.getAllByEnableStatus().collectList())
                .map(tuple -> {
                    var resultModel = tuple.getT1();
                    resultModel.setDiscountCouponList(tuple.getT2());
                    return resultModel;
                });
    }

    private void extractFromSettingList(StorePromoModel storePromoModel, List<SettingModel> settingModelList) {
        settingModelList.forEach(item -> {
            if (Constant.SHIPPING_PROMO_CONFIG.equalsIgnoreCase(item.getConfigName())) {
                storePromoModel.setStoreShippingFeePromoOn(item.getConfigValue().equalsIgnoreCase("true"));
                storePromoModel.setStoreShippingFeePromoType(item.getConfigType());
            }
            if (Constant.SIGNUP_PROMO_CONFIG.equalsIgnoreCase(item.getConfigName())) {
                storePromoModel.setStoreSignupDiscountPromoOn(item.getConfigValue().equalsIgnoreCase("true"));
                storePromoModel.setStoreSignupBonusPromoType(item.getConfigType());
            }
            if (Constant.DISCOUNT_COUPON_CONFIG.equalsIgnoreCase(item.getConfigName())) {
                storePromoModel.setStoreDiscountCouponPromoOn(item.getConfigValue().equalsIgnoreCase("true"));
                storePromoModel.setStoreDiscountCouponPromoType(item.getConfigType());
            }
        });
    }
}
