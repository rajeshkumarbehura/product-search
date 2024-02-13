package com.sellde.reward.service.model;

import com.sellde.reward.enums.StatusType;
import com.sellde.reward.util.Constant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.With;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@With
@AllArgsConstructor
public class CustomerBadgeModel {

    private UUID id;
    private UUID customerId;
    private Integer yearMonth;
    private BigDecimal totalPrice;
    private BigDecimal totalShippingPrice;
    private BigDecimal totalDiscountPrice;
    private BigDecimal totalRelyBulkPrice;
    private String defaultBadge;
    private String monthlyBadge;
    private String note;
    private StatusType status;

    /* only for evaluation */
    private String finalBadge;
    private List<MonthlyBadgeModel> customerBadgeRangeList;

    public CustomerBadgeModel() {
        defaultBadge = Constant.NOT_AVIALBLE;
        monthlyBadge = Constant.NOT_AVIALBLE;
        finalBadge = Constant.NOT_AVIALBLE;
        status = StatusType.ENABLE;
        totalRelyBulkPrice = BigDecimal.ZERO;
        totalPrice = BigDecimal.ZERO;
        totalDiscountPrice = BigDecimal.ZERO;
        totalShippingPrice = BigDecimal.ZERO;
        customerBadgeRangeList = new ArrayList<>();
    }

    public UUID getCustomerBadgeId() {
        return getId();
    }

    /* without shipping & exclude discount */
    public BigDecimal getActualSpentTotalPrice() {
        return totalPrice.subtract(totalShippingPrice).add(totalDiscountPrice);
    }

}
