package com.sellde.reward.rest;

import com.sellde.reward.service.DiscountCouponService;
import com.sellde.reward.service.SettingService;
import com.sellde.reward.service.model.DiscountCouponModel;
import com.sellde.reward.util.Constant;

import static com.sellde.reward.util.Utils.isNotEmpty;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("v1/discount-coupon")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DiscountCouponResource {

    @Autowired
    DiscountCouponService discountCouponService;

    @Autowired
    SettingService settingService;

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public Flux<DiscountCouponModel> getByParam(@RequestParam(required = false) List<String> couponNameList) {
        if (isNotEmpty(couponNameList)) {
            return discountCouponService.getByCouponNameList(couponNameList);
        }
        return discountCouponService.getAllForOps();
    }

    @PostMapping()
    public Flux<DiscountCouponModel> createOrUpdate(@RequestBody List<DiscountCouponModel> modelList) {
        return discountCouponService.createOrUpdate(modelList);
    }

    @PutMapping("/status")
    @ResponseStatus(HttpStatus.OK)
    public Mono<Map> updateDiscountCouponStatus(@RequestParam Boolean isStoreDiscountCouponPromoOn) {
        return settingService.updateStoreDiscountCouponPromoOn(isStoreDiscountCouponPromoOn)
                .map(flag -> new JSONObject()
                        .put(Constant.DISCOUNT_COUPON_CONFIG, flag)
                        .toMap()
                );
    }

    @GetMapping("/status")
    @ResponseStatus(HttpStatus.OK)
    public Mono<Map> getDiscountCouponStatus() {
        return settingService.isStoreDiscountCouponPromoOn()
                .map(flag -> new JSONObject()
                        .put(Constant.DISCOUNT_COUPON_CONFIG, flag)
                        .toMap()
                );
    }
}
