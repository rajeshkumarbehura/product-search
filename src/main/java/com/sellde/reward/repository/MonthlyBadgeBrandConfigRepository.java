package com.sellde.reward.repository;

import com.sellde.reward.domain.MonthlyBadgeBrandConfig;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.util.UUID;


@Repository
public interface MonthlyBadgeBrandConfigRepository extends R2dbcRepository<MonthlyBadgeBrandConfig, UUID> {

    Flux<MonthlyBadgeBrandConfig> findAllByMonthlyBadgeIdOrderByGoodsBrandName(UUID monthlyBadgeId);
}
