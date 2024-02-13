package com.sellde.reward.rest;

import com.sellde.reward.service.SettingService;
import com.sellde.reward.service.ShippingPromoService;
import com.sellde.reward.service.model.ShippingFeePromoModel;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("v1/shipping-fee-promo")
public class ShippingFeePromoResource {

    @Autowired
    private SettingService settingService;
    @Autowired
    private ShippingPromoService shippingPromoService;

    @PutMapping("/status")
    @ResponseStatus(HttpStatus.OK)
    public Mono<Map> updatePromoStatus(@RequestParam Boolean isShippingFeePromoOn) {
        return settingService.updateShippingFeePromo(isShippingFeePromoOn)
                .map(flag -> new JSONObject()
                        .put("isShippingFeePromoOn", flag)
                        .toMap()
                );
    }

    @GetMapping("/status")
    @ResponseStatus(HttpStatus.OK)
    public Mono<Map> getPromoStatus() {
        return settingService.isShippingFeePromoOn()
                .map(flag -> new JSONObject()
                        .put("isShippingFeePromoOn", flag)
                        .toMap()
                );
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.OK)
    public Flux<ShippingFeePromoModel> crateOrUpdate(@RequestBody List<ShippingFeePromoModel> shippingFeePromoModelList) {
        return shippingPromoService.create(shippingFeePromoModelList);
    }


    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public Flux<ShippingFeePromoModel> getAll() {
        return shippingPromoService.getAllPromos();
    }

}
