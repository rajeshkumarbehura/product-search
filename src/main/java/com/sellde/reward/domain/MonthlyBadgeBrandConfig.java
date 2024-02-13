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
@Table("monthly_badge_brand_config")
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
public class MonthlyBadgeBrandConfig extends AuditDomain {

    @Id
    @Column("id")
    private UUID id;

    @Column("goods_brand_id")
    private UUID goodsBrandId;

    @Column("goods_brand_name")
    private String goodsBrandName;

    @Column("monthly_badge_id")
    private UUID monthlyBadgeId;

    @Column("badge_discount_rate")
    private BigDecimal badgeDiscountRate;

    @Column("status")
    private StatusType status;
}