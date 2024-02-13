package com.sellde.reward.domain;

import com.sellde.reward.enums.StatusType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.util.UUID;


@Data
@Table("shipping_fee_promo")
@EqualsAndHashCode(callSuper = false)
public class ShippingFeePromo extends AuditDomain {

    @Id
    @Column("id")
    private UUID id;

    @Column("start_range")
    private BigDecimal startRange;

    @Column("end_range")
    private BigDecimal endRange;

    @Column("shipping_fee")
    private BigDecimal shippingFee;

    @Column("status")
    private StatusType status = StatusType.ENABLE;
}