package com.sellde.reward.rest;


import com.sellde.reward.service.CustomerBadgeService;
import com.sellde.reward.service.biz.CustomerBadgeReportService;
import com.sellde.reward.service.model.CustomerBadgeModel;
import com.sellde.reward.service.model.CustomerBadgeReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequestMapping("v1/customer-badge")
public class CustomerBadgeResource {

    @Autowired
    private CustomerBadgeService customerBadgeService;

    @Autowired
    private CustomerBadgeReportService customerBadgeReportService;

    @GetMapping()
    public Mono<CustomerBadgeModel> getByParams(@RequestParam UUID customerId) {
        return customerBadgeService.getByCustomerIdAndCurrentMonth(customerId);
    }

    @GetMapping("/report")
    public Flux<CustomerBadgeReport> getMonthlyReport() {
        return customerBadgeReportService.createReport();
    }

}
