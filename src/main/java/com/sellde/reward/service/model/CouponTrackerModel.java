package com.sellde.reward.service.model;

import lombok.Data;

import java.util.UUID;

@Data
public class CouponTrackerModel {
    private UUID id;
    private UUID customerId;
    private String couponName;
}
