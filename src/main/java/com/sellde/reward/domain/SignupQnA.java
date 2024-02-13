package com.sellde.reward.domain;

import com.sellde.reward.enums.StatusType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;


@Data
@Table("signup_qna")
@EqualsAndHashCode(callSuper = false)
public class SignupQnA extends AuditDomain {

    @Id
    @Column("id")
    private UUID id;

    @Column("phone_no")
    private long phoneNo;

    @Column("query_no")
    private int queryNo;

    @Column("query_en")
    private String queryEn;

    @Column("query_vn")
    private String queryVn;

    @Column("answer")
    private String answer;

    @Column("status")
    private StatusType status;

    public SignupQnA() {
        status = StatusType.ENABLE;
    }

}