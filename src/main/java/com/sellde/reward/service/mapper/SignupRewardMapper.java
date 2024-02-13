package com.sellde.reward.service.mapper;

import com.sellde.reward.domain.SignupReward;
import com.sellde.reward.service.model.SignupRewardModel;
import com.sellde.reward.service.model.SignupRewardModel.RangeModel;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
@Mapper(componentModel = "spring", uses = {})
public interface SignupRewardMapper extends EntityMapper<SignupRewardModel, SignupReward> {

    List<SignupReward> toRangeEntityList(List<RangeModel> dtoList);

    List<RangeModel> toRangeModelList(List<SignupReward> entityList);

}
