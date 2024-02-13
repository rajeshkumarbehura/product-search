package com.sellde.reward.domain;

import com.sellde.reward.enums.StatusType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;


@Data
@Table("discount_coupon")
@EqualsAndHashCode(callSuper = false)
public class DiscountCoupon extends AuditDomain {

    @Id
    @Column("id")
    private UUID id;

    @Column("coupon_name")
    private String couponName;

    @Column("description")
    private String description;

    @Column("has_fixed_time")
    private Boolean hasFixedTime;

    @Column("coupon_price")
    private BigDecimal couponPrice;

    @Column("display_no")
    private Integer displayNo;

    @Column("customer_count")
    private Integer customerCount;

    @Column("min_buying_range")
    private BigDecimal minBuyingRange;

    @Column("start_date")
    private LocalDate startDate;

    @Column("end_date")
    private LocalDate endDate;

    @Column("status")
    private StatusType status = StatusType.ENABLE;

}