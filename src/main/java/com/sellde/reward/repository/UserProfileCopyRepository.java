package com.sellde.reward.repository;

import com.sellde.reward.domain.UserProfileCopy;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
public interface UserProfileCopyRepository extends R2dbcRepository<UserProfileCopy, UUID> {
    Mono<UserProfileCopy> findOneByUserId(UUID userId);
}
