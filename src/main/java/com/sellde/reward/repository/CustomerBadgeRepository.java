package com.sellde.reward.repository;

import com.sellde.reward.domain.CustomerBadge;
import org.springframework.data.domain.Sort;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

@Repository
public interface CustomerBadgeRepository extends R2dbcRepository<CustomerBadge, UUID> {

    Mono<CustomerBadge> findOneByCustomerIdAndYearMonth(UUID customerId, Integer yearMonth);

//    Flux<CustomerBadge> findAllByYearMonthIsInOrderByCustomerIdYearMonth(List<Integer> yearMonthList);

    Flux<CustomerBadge> findAllByYearMonthIsIn(List<Integer> yearMonthList, Sort sort);

    Flux<CustomerBadge> findAllByCustomerIdAndYearMonthIsIn(UUID customerId, List<Integer> yearMonthList);
}
