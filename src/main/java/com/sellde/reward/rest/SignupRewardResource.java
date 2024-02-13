package com.sellde.reward.rest;

import com.sellde.reward.service.SettingService;
import com.sellde.reward.service.SignupRewardService;
import com.sellde.reward.service.model.SignupRewardModel;
import com.sellde.reward.util.Constant;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("v1/signup-reward")
public class SignupRewardResource {

    @Autowired
    private SignupRewardService signupRewardService;

    @Autowired
    private SettingService settingService;

    @GetMapping
    public Flux<SignupRewardModel> getAll() {
        return signupRewardService.getAll();
    }

    @PostMapping()
    public Flux<SignupRewardModel> create(@RequestBody List<SignupRewardModel> modelList) {
        return signupRewardService.createOrUpdate(modelList);
    }

    @PutMapping("/status")
    @ResponseStatus(HttpStatus.OK)
    public Mono<Map> updateRewardStatus(@RequestParam Boolean isStoreSignupDiscountPromoOn) {
        return settingService.updateStoreSignupDiscountPromoOn(isStoreSignupDiscountPromoOn)
                .map(flag -> new JSONObject()
                        .put(Constant.SIGNUP_PROMO_CONFIG, flag)
                        .toMap()
                );
    }

    @GetMapping("/status")
    @ResponseStatus(HttpStatus.OK)
    public Mono<Map> getRewardStatus() {
        return settingService.isStoreSignupDiscountPromoOn()
                .map(flag -> new JSONObject()
                        .put(Constant.SIGNUP_PROMO_CONFIG, flag)
                        .toMap()
                );
    }
}