package com.sellde.reward.rest.open;

import com.sellde.reward.service.biz.StorePromoBizService;
import com.sellde.reward.service.model.StorePromoModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("v1/open/store-promo")
public class OpenStorePromoResource {

    @Autowired
    private StorePromoBizService storePromoBizService;

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public Mono<StorePromoModel> getStorePromo() {
        return storePromoBizService.findPromos();
    }
}
