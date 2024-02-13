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
@Table("monthly_badge")
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
public class MonthlyBadge extends AuditDomain {

    @Id
    @Column("id")
    private UUID id;

    @Column("badge")
    private String badge;

    @Column("badge_index")
    private Integer badgeIndex;

    @Column("badge_value")
    private BigDecimal badgeValue;

    @Column("status")
    private StatusType status;
}