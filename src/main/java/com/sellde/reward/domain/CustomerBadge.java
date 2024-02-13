package com.sellde.reward.domain;

import com.sellde.reward.enums.StatusType;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.util.UUID;


@Data
@With
@Table("customer_badge")
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
public class CustomerBadge extends AuditDomain {

    @Id
    @Column("id")
    private UUID id;

    @Column("customer_id")
    private UUID customerId;

    @Column("year_month")
    private Integer yearMonth;

    @Column("total_price")
    private BigDecimal totalPrice;

    @Column("total_shipping_price")
    private BigDecimal totalShippingPrice;

    @Column("total_discount_price")
    private BigDecimal totalDiscountPrice;

    @Column("total_rely_bulk_price")
    private BigDecimal totalRelyBulkPrice;

    @Column("default_badge_id")
    private UUID defaultBadgeId;

    /* usually calculated based on previous month data */
    @Column("default_badge")
    private String defaultBadge;

    @Column("monthly_badge_id")
    private UUID monthlyBadgeId;

    @Column("monthly_badge")
    private String monthlyBadge;

    @Column("note")
    private String note;

    @Column("status")
    private StatusType status;
}