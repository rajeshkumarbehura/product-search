package com.sellde.reward.service.mapper;

import com.sellde.reward.domain.CouponTracker;
import com.sellde.reward.service.model.CouponTrackerModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;


@Component
@Mapper(componentModel = "spring", uses = {})
public interface CouponTrackerMapper extends EntityMapper<CouponTrackerModel, CouponTracker> {

    @Named("toUpdateEntity")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    CouponTracker toUpdateEntity(@MappingTarget CouponTracker entity, CouponTrackerModel model);
}
