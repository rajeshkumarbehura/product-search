package com.sellde.reward.domain;

import com.sellde.reward.enums.StatusType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;


@Data
@Table("setting")
@EqualsAndHashCode(callSuper = false)
public class Setting extends AuditDomain {

    @Id
    @Column("id")
    private UUID id;

    @Column("config_type")
    private String configType;

    @Column("config_name")
    private String configName;

    @Column("config_value")
    private String configValue;

    @Column("status")
    private StatusType status;

}