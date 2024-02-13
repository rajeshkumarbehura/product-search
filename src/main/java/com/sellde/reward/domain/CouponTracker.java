package com.sellde.reward.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sellde.reward.enums.StatusType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.Instant;
import java.util.UUID;


@Data
@Table("coupon_tracker")
@EqualsAndHashCode(callSuper = false)
public class CouponTracker {

    @Id
    @Column("id")
    private UUID id;

    @Column("customer_id")
    private UUID customerId;

    @Column("discount_coupon_id")
    private UUID discountCouponId;

    @Column("status")
    private StatusType status = StatusType.ENABLE;

    @CreatedDate
    @Column("created_date")
    @JsonIgnore
    protected Instant createdDate = Instant.now();

}