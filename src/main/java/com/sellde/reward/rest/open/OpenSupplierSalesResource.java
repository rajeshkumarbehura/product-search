package com.sellde.reward.rest.open;


import com.sellde.reward.service.biz.SupplierSalesBizService;
import com.sellde.reward.service.model.SupplierSalesModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import static com.sellde.reward.service.model.SupplierSalesModel.SqsMessage;
import static com.sellde.reward.service.model.SupplierSalesModel.SqsMessageResponse;

@RestController
@RequestMapping("v1/open/supplier-sales")
public class OpenSupplierSalesResource {

    @Autowired
    private SupplierSalesBizService supplierSalesBizService;

    @Value("${sellde.supply-reward-api-key}")
    private String supplyRewardApiKey;

    @PostMapping()
    public Mono<ResponseEntity<SqsMessageResponse>> createOrUpdateBasket(
            @RequestHeader(required = false, value = "supply-reward-api-key") String apiKey,
            @RequestBody SqsMessage sqsMessage) {
        if (supplyRewardApiKey.equalsIgnoreCase(apiKey)) {
            return supplierSalesBizService.
                    processBasketAndUpdateCustomerBadge(sqsMessage.getRequest())
                    .map(item -> new SupplierSalesModel.SqsMessageResponse()
                            .withMessage("successfully done")
                            .withStatus("200"))
                    .map(item -> ResponseEntity.status(HttpStatus.OK).body(item));
        } else {
            var response = new SqsMessageResponse()
                    .withMessage("invalid key")
                    .withStatus("401");
            return Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response));
        }
    }
}
