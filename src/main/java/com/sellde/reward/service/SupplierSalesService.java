package com.sellde.reward.service;

import com.sellde.reward.repository.BasketSalesCustomRepository;
import com.sellde.reward.repository.SupplierSalesRepository;
import com.sellde.reward.service.mapper.SupplierSalesMapper;
import com.sellde.reward.service.model.SupplierSalesModel;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SupplierSalesService {

    @Autowired
    SupplierSalesRepository supplierSalesRepository;

    @Autowired
    BasketSalesCustomRepository basketSalesCustomRepository;

    @Autowired
    SupplierSalesMapper supplierSalesMapper;

    public Mono<SupplierSalesModel.BasketResponse> createFromBasket(SupplierSalesModel.BasketRequest request) {
        return supplierSalesRepository
                .findByOrderNo(request.getOrderNo())
                .map(existingItem -> supplierSalesMapper.toUpdateEntity(existingItem, request))
                .defaultIfEmpty(supplierSalesMapper.toEntityFromBasket(request))
                .flatMap(supplierSalesRepository::save)
                .map(supplierSalesMapper::toBasketResponse);
    }

    public Mono<SupplierSalesModel.BasketResponse> getMaxOrderNoForCustomer(UUID customerId) {
        return supplierSalesRepository.findMaxValidOrderForCustomer(customerId)
                .map(supplierSalesMapper::toBasketResponse);
    }

    public Mono<SupplierSalesModel.SumOfBasketSales> getSumOfBasketSalesByCustomer(SupplierSalesModel.BasketRequest request) {
        var year = request.getOrderDate().getYear();
        var month = request.getOrderDate().getMonth().getValue();
        return basketSalesCustomRepository
                .getSumOfSalesOfCustomer(request.getCustomerId(), year, month);
    }

}
