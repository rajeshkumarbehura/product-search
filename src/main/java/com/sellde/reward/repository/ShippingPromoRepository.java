package com.sellde.reward.repository;

import com.sellde.reward.domain.ShippingFeePromo;
import com.sellde.reward.enums.StatusType;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.util.UUID;

@Repository
public interface ShippingPromoRepository extends R2dbcRepository<ShippingFeePromo, UUID> {
    Flux<ShippingFeePromo> findAllByStatus(StatusType status);
}
