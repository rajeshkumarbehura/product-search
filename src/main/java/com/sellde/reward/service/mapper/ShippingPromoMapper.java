package com.sellde.reward.service.mapper;

import com.sellde.reward.domain.ShippingFeePromo;
import com.sellde.reward.service.model.ShippingFeePromoModel;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;


@Component
@Mapper(componentModel = "spring", uses = {})
public interface ShippingPromoMapper extends EntityMapper<ShippingFeePromoModel, ShippingFeePromo> {
}

