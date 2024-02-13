package com.sellde.reward.service.mapper;

import com.sellde.reward.domain.Setting;
import com.sellde.reward.service.model.SettingModel;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;


@Component
@Mapper(componentModel = "spring", uses = {})
public interface SettingMapper extends EntityMapper<SettingModel, Setting> {
}

