package com.sellde.reward.repository;

import com.sellde.reward.domain.DiscountCoupon;
import com.sellde.reward.enums.StatusType;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;


@Repository
public interface DiscountCouponRepository extends R2dbcRepository<DiscountCoupon, UUID> {

    Flux<DiscountCoupon> findAllByStatus(StatusType status);

    Mono<DiscountCoupon> findByCouponName(String couponName);

    Flux<DiscountCoupon> findByCouponNameIsIn(List<String> couponNameList,StatusType status);
}
