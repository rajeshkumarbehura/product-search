package com.sellde.reward.repository;

import com.sellde.reward.domain.GoodsSearchCount;
import com.sellde.reward.enums.StatusType;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
public interface GoodsSearchCountRepository extends R2dbcRepository<GoodsSearchCount, UUID> {

    Mono<GoodsSearchCount> findOneByKeywordAndStatus(String keyword, StatusType statusType);

    Flux<GoodsSearchCount> findAllByStatus(StatusType status, PageRequest pageRequest);

}
