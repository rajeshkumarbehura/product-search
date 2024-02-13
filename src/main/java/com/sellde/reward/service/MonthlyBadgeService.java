package com.sellde.reward.service;

import com.sellde.reward.enums.StatusType;
import com.sellde.reward.repository.MonthlyBadgeRepository;
import com.sellde.reward.service.mapper.MonthlyBadgeMapper;
import com.sellde.reward.service.model.MonthlyBadgeModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@Service
public class MonthlyBadgeService {


    @Autowired
    private MonthlyBadgeRepository monthlyBadgeRepository;


    @Autowired
    private MonthlyBadgeMapper monthlyBadgeMapper;

    public Flux<MonthlyBadgeModel> getAll() {
        return monthlyBadgeRepository.findAll()
                .map(monthlyBadgeMapper::toModel);
    }

    public Flux<MonthlyBadgeModel> getAllEnabled() {
        return monthlyBadgeRepository.findAllByStatus(StatusType.ENABLE)
                .map(monthlyBadgeMapper::toModel);
    }

    public Mono<Map<String, MonthlyBadgeModel>> getAllEnabledIntoMap() {
        return monthlyBadgeRepository.findAllByStatus(StatusType.ENABLE)
                .map(monthlyBadgeMapper::toModel)
                .collectList()
                .map(list -> {
                    var map = new HashMap<String, MonthlyBadgeModel>();
                    list.forEach(item -> {
                        map.put(item.getBadge(), item);
                    });
                    return map;
                });
    }

//    public Mono<MonthlyBadgeModel> getBadgeByTotalPrice(BigDecimal price) {
//        return monthlyBadgeRepository.findAllByBadgeValueLessThanEqualAndStatus(price)
//                .map(monthlyBadgeMapper::toModel);
//    }
//
//    public Mono<MonthlyBadgeModel> getByBadge(String badgeName) {
//        return monthlyBadgeRepository.findOneByBadgeAndStatus(badgeName, StatusType.ENABLE)
//                .map(monthlyBadgeMapper::toModel);
//    }

    public Mono<MonthlyBadgeModel> create(MonthlyBadgeModel model) {
        model.setId(null);
        model.setBadge(model.getBadge().toUpperCase());
        return monthlyBadgeRepository.save(monthlyBadgeMapper.toEntity(model))
                .map(monthlyBadgeMapper::toModel);
    }

    public Mono<MonthlyBadgeModel> update(MonthlyBadgeModel model) {
        return monthlyBadgeRepository.findById(model.getMonthlyBadgeId())
                .map(item -> {
                    item.setStatus(model.getStatus());
                    item.setLastModifiedDate(Instant.now());
                    return item;
                }).flatMap(monthlyBadgeRepository::save)
                .map(monthlyBadgeMapper::toModel);
    }
}
