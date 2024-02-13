package com.sellde.reward.repository;

import com.sellde.reward.domain.Contactus;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ContactusRepository extends R2dbcRepository<Contactus, UUID> {
}
