package com.sellde.reward.service.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Data
@With
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CustomerBadgeReport {
    UUID customerId;
    String customerName;
    long phoneNo;
    int lastMonth;
    String lastMonthName;
    CustomerBadgeModel lastMonthBadge;
    int currentMonth;
    String currentMonthName;
    CustomerBadgeModel currentMonthBadge;
    int nextMonth;
    String nextMonthName;
    CustomerBadgeModel nextMonthBadge;
}

