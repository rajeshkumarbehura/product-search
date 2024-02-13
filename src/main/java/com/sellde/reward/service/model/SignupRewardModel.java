package com.sellde.reward.service.model;

import com.sellde.reward.enums.StatusType;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Data
public class SignupRewardModel {
    private String configValue;
    private String configName;
    private String rewardType;
    private List<RangeModel> rangeList;

    @Data
    public static class RangeModel {
        private UUID id;
        private BigDecimal startRange;
        private BigDecimal endRange;
        private BigDecimal discountPrice;
        private StatusType status;
    }
}


