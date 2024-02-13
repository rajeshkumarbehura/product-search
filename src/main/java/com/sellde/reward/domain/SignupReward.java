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
@Table("signup_reward")
@EqualsAndHashCode(callSuper = false)
public class SignupReward extends AuditDomain {

    @Id
    @Column("id")
    private UUID id;

    @Column("reward_type")
    private String rewardType;

    @Column("config_name")
    private String configName;

    @Column("config_value")
    private String configValue;

    @Column("start_range")
    private BigDecimal startRange;

    @Column("end_range")
    private BigDecimal endRange;

    @Column("discount_price")
    private BigDecimal discountPrice;

    @Column("status")
    private StatusType status;

}