package com.sellde.reward.service.mapper;

import com.sellde.reward.domain.MonthlyBadgeBrandConfig;
import com.sellde.reward.service.model.MonthlyBadgeBrandConfigModel;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring", uses = {})
public interface MonthlyBadgeConfigMapper extends EntityMapper<MonthlyBadgeBrandConfigModel, MonthlyBadgeBrandConfig> {
}
