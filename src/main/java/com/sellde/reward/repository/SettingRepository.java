package com.sellde.reward.repository;

import com.sellde.reward.domain.Setting;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

@Repository
public interface SettingRepository extends R2dbcRepository<Setting, UUID> {

    Mono<Setting> findDistinctByConfigName(String configName);

    Flux<Setting> findAllByConfigNameIsIn(List<String> configNameList);

}
