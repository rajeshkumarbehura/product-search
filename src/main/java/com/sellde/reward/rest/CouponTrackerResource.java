package com.sellde.reward.rest;

import com.sellde.reward.service.CouponTrackerService;
import com.sellde.reward.service.model.CouponTrackerModel;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("v1/coupon-tracker")
public class CouponTrackerResource {

    @Autowired
    private CouponTrackerService couponTrackerService;

    @PostMapping()
    public Mono<CouponTrackerModel> customerAvailablePromo(@RequestBody CouponTrackerModel model) {
        return couponTrackerService.createUsingCouponName(model);
    }

    @GetMapping("/can-apply-coupon")
    public Mono<Map> canCustomerApplyCoupon(@RequestParam UUID customerId, @RequestParam String couponName) {
        return couponTrackerService.canCustomerApplyCoupon(customerId, couponName)
                .map(flag -> new JSONObject()
                        .put("canApplyCoupon", flag).toMap());
    }

}
