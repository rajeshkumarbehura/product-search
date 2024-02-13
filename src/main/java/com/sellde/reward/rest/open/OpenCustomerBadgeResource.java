package com.sellde.reward.rest.open;


import com.sellde.reward.service.CustomerBadgeService;
import com.sellde.reward.service.MonthlyBadgeService;
import com.sellde.reward.service.model.CustomerBadgeModel;
import com.sellde.reward.service.model.MonthlyBadgeModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.UUID;

@RestController
@RequestMapping("v1/open/customer-badge")
public class OpenCustomerBadgeResource {

    @Autowired
    private CustomerBadgeService customerBadgeService;

    @Autowired
    private MonthlyBadgeService monthlyBadgeService;

    @GetMapping("/range")
    public Flux<MonthlyBadgeModel> getByParams() {
//   return customerBadgeService.getBadgeValues();
        return monthlyBadgeService.getAllEnabled();
    }

}
