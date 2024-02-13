package com.sellde.reward.service.mapper;

import com.sellde.reward.domain.SupplierSales;
import com.sellde.reward.service.model.SupplierSalesModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;


@Component
@Mapper(componentModel = "spring", uses = {})
public interface SupplierSalesMapper {

    SupplierSales toEntityFromBasket(SupplierSalesModel.BasketRequest request);

    SupplierSalesModel.BasketResponse toBasketResponse(SupplierSales entity);

    @Named("toUpdateEntity")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "lastModifiedDate", ignore = true)
    SupplierSales toUpdateEntity(@MappingTarget SupplierSales entity, SupplierSalesModel.BasketRequest request);
}
