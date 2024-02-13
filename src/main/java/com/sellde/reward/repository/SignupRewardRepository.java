package com.sellde.reward.repository;

import com.sellde.reward.domain.SignupReward;
import com.sellde.reward.enums.StatusType;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.util.UUID;


@Repository
public interface SignupRewardRepository extends R2dbcRepository<SignupReward, UUID> {
    @Query("""
            select * from signup_reward
            order by reward_type
            """)
    Flux<SignupReward> findAllOrderByRewardType();

    Flux<SignupReward> findAllByStatusOrderByRewardType(StatusType statusType);
}
