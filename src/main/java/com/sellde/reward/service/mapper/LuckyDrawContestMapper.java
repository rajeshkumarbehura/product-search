package com.sellde.reward.service.mapper;

import com.sellde.reward.domain.LuckyDrawContest;
import com.sellde.reward.service.model.LuckyDrawContestModel;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring", uses = {})
public interface LuckyDrawContestMapper extends EntityMapper<LuckyDrawContestModel, LuckyDrawContest> {
}

