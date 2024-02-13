package com.sellde.reward.service.model;

import com.sellde.reward.domain.MonthlyBadgeBrandConfig;
import com.sellde.reward.enums.StatusType;

import java.util.UUID;

public class MonthlyBadgeBrandConfigModel extends MonthlyBadgeBrandConfig {
    
    public MonthlyBadgeBrandConfigModel() {
        setStatus(StatusType.ENABLE);
    }

    public UUID getMonthlyBadgeBrandConfigId() {
        return getId();
    }
}
