package com.sellde.reward.service.mapper;

import com.sellde.reward.domain.CustomerBadge;
import com.sellde.reward.service.model.CustomerBadgeModel;
import com.sellde.reward.service.model.SupplierSalesModel;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring", uses = {})
public interface CustomerBadgeMapper extends EntityMapper<CustomerBadgeModel, CustomerBadge> {
    CustomerBadge toEntityFromCustomerBadgeRequest(SupplierSalesModel.CustomerBadgeRequest request);
}

