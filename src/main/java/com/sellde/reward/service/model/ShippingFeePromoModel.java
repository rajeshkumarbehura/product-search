package com.sellde.reward.service.model;

import com.sellde.reward.enums.StatusType;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class ShippingFeePromoModel {
    private UUID id;
    private BigDecimal startRange;
    private BigDecimal endRange;
    private BigDecimal shippingFee;
    private StatusType status;
}
