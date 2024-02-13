package com.sellde.reward.service.model;

import com.sellde.reward.domain.MonthlyBadge;

import java.util.UUID;

public class MonthlyBadgeModel extends MonthlyBadge {

    public UUID getMonthlyBadgeId() {
        return getId();
    }
}
