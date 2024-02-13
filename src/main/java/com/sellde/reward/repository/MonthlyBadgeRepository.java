package com.sellde.reward.repository;

import com.sellde.reward.domain.MonthlyBadge;
import com.sellde.reward.enums.StatusType;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.UUID;


@Repository
public interface MonthlyBadgeRepository extends R2dbcRepository<MonthlyBadge, UUID> {

    @Query("select * from monthly_badge mb where mb.badge_value <= :badgeValue and status = 'ENABLE' order by badge_index desc limit 1")
    Mono<MonthlyBadge> findAllByBadgeValueLessThanEqualAndStatus(BigDecimal badgeValue);

    Flux<MonthlyBadge> findAllByStatus(StatusType status);

    Mono<MonthlyBadge> findOneByBadgeAndStatus(String badge, StatusType statusType);

}
