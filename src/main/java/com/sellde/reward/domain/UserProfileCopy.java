package com.sellde.reward.domain;

import com.sellde.reward.enums.StatusType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;


@Data
@Table("user_profile_copy")
@EqualsAndHashCode(callSuper = false)
public class UserProfileCopy extends AuditDomain {

    @Id
    @Column("id")
    private UUID id;

    @Column("user_id")
    private UUID userId;

    @Column("user_name")
    private String userName;

    @Column("phone_no")
    private Long phoneNo;

    @Column("subgroup")
    private String subgroup;

    @Column("user_type")
    private String userType;

    @Column("status")
    private StatusType status;
}