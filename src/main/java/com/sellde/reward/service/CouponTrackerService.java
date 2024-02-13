package com.sellde.reward.service;

import com.sellde.reward.enums.StatusType;
import com.sellde.reward.repository.CouponTrackerRepository;
import com.sellde.reward.service.mapper.CouponTrackerMapper;
import com.sellde.reward.service.model.CouponTrackerModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
public class CouponTrackerService {

    @Autowired
    private CouponTrackerMapper couponTrackerMapper;

    @Autowired
    private DiscountCouponService discountCouponService;

    @Autowired
    private CouponTrackerRepository couponTrackerRepository;

    public Mono<CouponTrackerModel> createUsingCouponName(CouponTrackerModel model) {
        // get coupon id by  coupon Name
        return discountCouponService.getByCouponName(model.getCouponName())
                .map(item -> {
                    // add coupon id
                    var domain = couponTrackerMapper.toEntity(model);
                    domain.setDiscountCouponId(item.getId());
                    return domain;
                })
                .flatMap(couponTrackerRepository::save)
                .map(couponTrackerMapper::toModel)
                .doOnNext(savedModel -> savedModel.setCouponName(model.getCouponName()));
    }

    public Mono<Boolean> canCustomerApplyCoupon(UUID customerId, String couponName) {
        return discountCouponService.getByCouponName(couponName)
                .zipWhen(couponModel -> couponTrackerRepository
                        .countCustomerAppliedCouponWithStatus(couponModel.getId(), customerId, StatusType.ENABLE))
                .map(tuple -> {
                    var allowedCount = tuple.getT1().getCustomerCount();
                    var actualCount = tuple.getT2();
                    if (allowedCount > actualCount) {
                        return true;
                    }
                    return false;
                });
    }

}
