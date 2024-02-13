package com.sellde.reward.repository;

import com.sellde.reward.domain.GoodsSearch;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.util.UUID;

@Repository
public interface GoodsSearchRepository extends R2dbcRepository<GoodsSearch, UUID> {

    @Query("""
            with tmp as (select track_or_customer_id, keyword, count(id)
                         from goods_search gs
                         where track_or_customer_id = :customer_id
                         group by track_or_customer_id, keyword)
            select * from tmp order by count limit 10
            """)
    Flux<GoodsSearch> findTopKeywordsByCustomerId(UUID customerId);
}
