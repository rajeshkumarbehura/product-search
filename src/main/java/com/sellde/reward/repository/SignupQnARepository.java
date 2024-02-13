package com.sellde.reward.repository;

import com.sellde.reward.domain.SignupQnA;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.util.UUID;

@Repository
public interface SignupQnARepository extends R2dbcRepository<SignupQnA, UUID> {

    Flux<SignupQnA> findAllByPhoneNoOrderByQueryNo(long phoneNo);

    Flux<SignupQnA> findAllByPhoneNoAndAnswerIsNotNullOrderByQueryNo(long phoneNo);
}
