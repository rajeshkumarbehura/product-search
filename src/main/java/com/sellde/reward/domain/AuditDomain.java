package com.sellde.reward.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.relational.core.mapping.Column;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;

/**
 * Base abstract class for entities which will hold definitions for created, last modified, created by,
 * last modified by attributes.
 */
@Data
public abstract class AuditDomain implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @CreatedDate
    @Column("created_date")
    @JsonIgnore
    protected Instant createdDate;


    @LastModifiedDate
    @Column("last_modified_date")
    @JsonIgnore
    protected Instant lastModifiedDate;

}
