package com.sellde.reward.rest;


import com.sellde.reward.service.MonthlyBadgeBrandConfigService;
import com.sellde.reward.service.MonthlyBadgeService;
import com.sellde.reward.service.model.MonthlyBadgeBrandConfigModel;
import com.sellde.reward.service.model.MonthlyBadgeModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequestMapping("v1/monthly-badge")
public class MonthlyBadgeResource {

    @Autowired
    private MonthlyBadgeService monthlyBadgeService;

    @Autowired
    private MonthlyBadgeBrandConfigService monthlyBadgeConfigService;

    @GetMapping()
    public Flux<MonthlyBadgeModel> getAll() {
        return monthlyBadgeService.getAll();
    }

    @PostMapping()
    public Mono<MonthlyBadgeModel> create(@RequestBody MonthlyBadgeModel model) {
        return monthlyBadgeService.create(model);
    }

    @PutMapping("/{monthlyBadgeId}")
    public Mono<MonthlyBadgeModel> update(@PathVariable UUID monthlyBadgeId, @RequestBody MonthlyBadgeModel model) {
        model.setId(monthlyBadgeId);
        return monthlyBadgeService.update(model);
    }

    @GetMapping("/{monthlyBadgeId}/config")
    public Flux<MonthlyBadgeBrandConfigModel> getBrandConfig(@PathVariable UUID monthlyBadgeId) {
        return monthlyBadgeConfigService.getByMonthlyBadgeId(monthlyBadgeId);
    }

    @PostMapping("/{monthlyBadgeId}/config")
    public Flux<MonthlyBadgeBrandConfigModel> createBrandConfig(@PathVariable UUID monthlyBadgeId,
                                                                @RequestBody MonthlyBadgeBrandConfigModel model) {
        model.setMonthlyBadgeId(monthlyBadgeId);
        return monthlyBadgeConfigService.create(model);
    }

}
