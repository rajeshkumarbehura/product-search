package com.sellde.reward.service.mapper;

import com.sellde.reward.domain.MonthlyBadge;
import com.sellde.reward.service.model.MonthlyBadgeModel;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring", uses = {})
public interface MonthlyBadgeMapper extends EntityMapper<MonthlyBadgeModel, MonthlyBadge> {
}
