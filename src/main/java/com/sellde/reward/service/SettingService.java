package com.sellde.reward.service;

import com.sellde.reward.repository.SettingRepository;
import com.sellde.reward.service.mapper.SettingMapper;
import com.sellde.reward.service.model.SettingModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;

import static com.sellde.reward.util.Constant.*;

@Service
public class SettingService {


    @Autowired
    private SettingRepository settingRepository;
    @Autowired
    private SettingMapper settingMapper;

    public Mono<Boolean> isShippingFeePromoOn() {
        return settingRepository
                .findDistinctByConfigName(SHIPPING_PROMO_CONFIG)
                .map(domain -> domain.getConfigValue().equalsIgnoreCase("true"));
    }

    public Mono<SettingModel> getShippingFeePromoOn() {
        return settingRepository
                .findDistinctByConfigName(SHIPPING_PROMO_CONFIG)
                .map(settingMapper::toModel)
                ;
    }

    public Flux<SettingModel> getStorePromoList() {
        var promoNameList = Arrays.asList(SHIPPING_PROMO_CONFIG,
                SIGNUP_PROMO_CONFIG,
                DISCOUNT_COUPON_CONFIG
        );
        return settingRepository
                .findAllByConfigNameIsIn(promoNameList)
                .map(settingMapper::toModel)
                ;
    }

    public Mono<Boolean> updateShippingFeePromo(Boolean isShippingFeePromoOn) {
        return settingRepository.findDistinctByConfigName(SHIPPING_PROMO_CONFIG)
                .doOnNext(domain -> {
                    domain.setConfigValue(isShippingFeePromoOn.toString());
                }).flatMap(settingRepository::save)
                .map(domain -> {
                    return domain.getConfigValue().equalsIgnoreCase("true");
                });

    }

    public Mono<Boolean> updateStoreSignupDiscountPromoOn(Boolean isStoreSignupDiscountPromoOn) {
        return settingRepository.findDistinctByConfigName(SIGNUP_PROMO_CONFIG)
                .doOnNext(domain -> {
                    domain.setConfigValue(isStoreSignupDiscountPromoOn.toString());
                }).flatMap(settingRepository::save)
                .map(domain -> domain.getConfigValue().equalsIgnoreCase("true"));

    }

    public Mono<Boolean> updateStoreDiscountCouponPromoOn(Boolean isStoreDiscountCouponPromoOn) {
        return settingRepository.findDistinctByConfigName(DISCOUNT_COUPON_CONFIG)
                .doOnNext(domain -> {
                    domain.setConfigValue(isStoreDiscountCouponPromoOn.toString());
                }).flatMap(settingRepository::save)
                .map(domain -> domain.getConfigValue().equalsIgnoreCase("true"));

    }

    public Mono<Boolean> isStoreSignupDiscountPromoOn() {
        return settingRepository
                .findDistinctByConfigName(SIGNUP_PROMO_CONFIG)
                .map(domain -> domain.getConfigValue().equalsIgnoreCase("true"));
    }

    public Mono<Boolean> isStoreDiscountCouponPromoOn() {
        return settingRepository
                .findDistinctByConfigName(DISCOUNT_COUPON_CONFIG)
                .map(domain -> domain.getConfigValue().equalsIgnoreCase("true"));
    }
}
