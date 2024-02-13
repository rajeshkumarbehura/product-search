package com.sellde.reward.enums;

public enum CartStatus {
    /*
     Pending Accept
     → Pending Delivery
     → Pending Received
     → Completed
     → Cancel
    */
    PLACED,
    PENDING_ACCEPT,
    PENDING_DELIVERY,
    PENDING_RECEIVED,
    READY_PICKUP,
    CANCELLED,
    COMPLETED,
    NA,
}