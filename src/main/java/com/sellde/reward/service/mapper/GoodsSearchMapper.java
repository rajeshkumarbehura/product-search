package com.sellde.reward.service.mapper;

import com.sellde.reward.domain.GoodsSearch;
import com.sellde.reward.service.model.GoodsSearchModel;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring", uses = {})
public interface GoodsSearchMapper {
    GoodsSearch fromRequest(GoodsSearchModel.Request request);
}

