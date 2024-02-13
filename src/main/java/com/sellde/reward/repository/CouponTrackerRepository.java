package com.sellde.reward.repository;

import com.sellde.reward.domain.CouponTracker;
import com.sellde.reward.enums.StatusType;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
public interface CouponTrackerRepository extends R2dbcRepository<CouponTracker, UUID> {

    @Query("""
            select coalesce (count(id),0) from coupon_tracker 
            where customer_id = :customerId 
            and discount_coupon_id = :couponId
            and status = :status
            """)
    Mono<Integer> countCustomerAppliedCouponWithStatus(UUID couponId, UUID customerId, StatusType status);

}

