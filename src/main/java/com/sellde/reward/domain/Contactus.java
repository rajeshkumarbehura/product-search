package com.sellde.reward.domain;

import com.sellde.reward.enums.ContactusStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;


@Data
@Table("contactus")
@EqualsAndHashCode(callSuper = false)
public class Contactus extends AuditDomain {

    @Id
    @Column("id")
    private UUID id;

    @Column("customer_id")
    private UUID customerId;

    @Column("phone_no")
    private String phoneNo;

    @Column("query")
    private String query;

    @Column("comment")
    private String comment;

    @Column("status")
    private ContactusStatus status = ContactusStatus.NEW;
}