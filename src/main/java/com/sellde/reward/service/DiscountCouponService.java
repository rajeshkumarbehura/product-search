package com.sellde.reward.service;

import com.sellde.reward.enums.StatusType;
import com.sellde.reward.repository.DiscountCouponRepository;
import com.sellde.reward.service.mapper.DiscountCouponMapper;
import com.sellde.reward.service.model.DiscountCouponModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class DiscountCouponService {

    @Autowired
    private DiscountCouponRepository discountCouponRepository;

    @Autowired
    private DiscountCouponMapper discountCouponMapper;


    public Flux<DiscountCouponModel> getAllForOps() {
        return discountCouponRepository.findAll()
                .map(item -> discountCouponMapper.toModel(item));
    }

    public Mono<DiscountCouponModel> createOrUpdate(DiscountCouponModel model) {
        return discountCouponRepository
                .save(discountCouponMapper.toEntity(model))
                .map(item -> discountCouponMapper.toModel(item));
    }

    public Flux<DiscountCouponModel> createOrUpdate(List<DiscountCouponModel> modelList) {
        var domainList = discountCouponMapper.toEntity(modelList);
        return discountCouponRepository
                .saveAll(domainList)
                .map(item -> discountCouponMapper.toModel(item));
    }

    public Flux<DiscountCouponModel> getAllByEnableStatus() {
        return discountCouponRepository.findAllByStatus(StatusType.ENABLE)
                .map(discountCouponMapper::toModel);
    }

    public Mono<DiscountCouponModel> getByCouponName(String couponName) {
        return discountCouponRepository.findByCouponName(couponName)
                .map(discountCouponMapper::toModel);
    }

    public Flux<DiscountCouponModel> getByCouponNameList(List<String> couponNameList) {
        return discountCouponRepository.findByCouponNameIsIn(couponNameList,StatusType.ENABLE)
                .map(discountCouponMapper::toModel);
    }
}
