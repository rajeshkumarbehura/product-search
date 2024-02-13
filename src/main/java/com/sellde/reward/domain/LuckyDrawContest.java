package com.sellde.reward.domain;

import com.sellde.reward.enums.GiftType;
import com.sellde.reward.enums.StatusType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;


@Data
@Table("lucky_draw_contest")
@EqualsAndHashCode(callSuper = false)
public class LuckyDrawContest extends AuditDomain {

    @Id
    @Column("id")
    private UUID id;

    @Column("phone_no")
    private String phoneNo;

    @Column("gift_type")
    private GiftType giftType = GiftType.NA;

    @Column("status")
    private StatusType status = StatusType.ENABLE;
}