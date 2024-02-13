package com.sellde.reward.service.mapper;

import com.sellde.reward.domain.DiscountCoupon;
import com.sellde.reward.domain.SignupReward;
import com.sellde.reward.service.model.DiscountCouponModel;
import com.sellde.reward.service.model.SignupRewardModel;
import com.sellde.reward.service.model.SignupRewardModel.RangeModel;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
@Mapper(componentModel = "spring", uses = {})
public interface DiscountCouponMapper extends EntityMapper<DiscountCouponModel, DiscountCoupon> {
}
