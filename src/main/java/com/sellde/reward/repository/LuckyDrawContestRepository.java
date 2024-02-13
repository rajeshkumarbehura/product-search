package com.sellde.reward.repository;

import com.sellde.reward.domain.LuckyDrawContest;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
public interface LuckyDrawContestRepository extends R2dbcRepository<LuckyDrawContest, UUID> {

    @Query("""
            select * from lucky_draw_contest 
            where phone_no = :phoneNo
            and to_char(created_date,'dd-MM-YYYY') = :createdDateValue
            """)
    Mono<LuckyDrawContest> findBYPhoneAndDay(String phoneNo, String createdDateValue);
}
