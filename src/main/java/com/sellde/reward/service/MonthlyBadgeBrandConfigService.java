package com.sellde.reward.service;

import com.sellde.reward.repository.MonthlyBadgeBrandConfigRepository;
import com.sellde.reward.service.mapper.MonthlyBadgeConfigMapper;
import com.sellde.reward.service.model.MonthlyBadgeBrandConfigModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.UUID;

@Service
public class MonthlyBadgeBrandConfigService {

    @Autowired
    MonthlyBadgeBrandConfigRepository monthlyBadgeConfigRepository;

    @Autowired
    MonthlyBadgeConfigMapper monthlyBadgeConfigMapper;

    public Flux<MonthlyBadgeBrandConfigModel> getByMonthlyBadgeId(UUID monthlyBadgeId) {
        return monthlyBadgeConfigRepository.findAllByMonthlyBadgeIdOrderByGoodsBrandName(monthlyBadgeId)
                .map(monthlyBadgeConfigMapper::toModel);
    }

    public Flux<MonthlyBadgeBrandConfigModel> create(MonthlyBadgeBrandConfigModel model) {
        if (model.getId() != null) {
            return update(model);
        }
        return monthlyBadgeConfigRepository.save(monthlyBadgeConfigMapper.toEntity(model))
                .thenMany(monthlyBadgeConfigRepository.findAllByMonthlyBadgeIdOrderByGoodsBrandName(model.getMonthlyBadgeId()))
                .map(monthlyBadgeConfigMapper::toModel);
    }

    public Flux<MonthlyBadgeBrandConfigModel> update(MonthlyBadgeBrandConfigModel model) {
        return monthlyBadgeConfigRepository.findById(model.getId())
                .map(item -> monthlyBadgeConfigMapper.toUpdateEntity(item, model))
                .flatMap(monthlyBadgeConfigRepository::save)
                .thenMany(monthlyBadgeConfigRepository.findAllByMonthlyBadgeIdOrderByGoodsBrandName(model.getMonthlyBadgeId()))
                .map(monthlyBadgeConfigMapper::toModel);
    }
}
