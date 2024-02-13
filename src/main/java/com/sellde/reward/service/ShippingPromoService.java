package com.sellde.reward.service;

import com.sellde.reward.enums.StatusType;
import com.sellde.reward.repository.ShippingPromoRepository;
import com.sellde.reward.service.mapper.ShippingPromoMapper;
import com.sellde.reward.service.model.ShippingFeePromoModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.List;

@Service
public class ShippingPromoService {

    @Autowired
    private ShippingPromoRepository shippingPromoRepository;

    @Autowired
    private ShippingPromoMapper shippingPromoMapper;

    @Autowired
    public Flux<ShippingFeePromoModel> getAllActivePromos() {
        return shippingPromoRepository
                .findAllByStatus(StatusType.ENABLE)
                .map(shippingPromoMapper::toModel);
    }

    @Autowired
    public Flux<ShippingFeePromoModel> getAllPromos() {
        return shippingPromoRepository
                .findAll()
                .map(shippingPromoMapper::toModel);
    }

    public Flux<ShippingFeePromoModel> create(List<ShippingFeePromoModel> shippingFeePromoList) {
        var domainList = shippingPromoMapper.toEntity(shippingFeePromoList);
        return shippingPromoRepository.saveAll(domainList)
                .map(shippingPromoMapper::toModel);
    }

}
