package com.sellde.reward.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sellde.reward.enums.StatusType;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.Instant;
import java.util.UUID;


@Data
@Table("goods_search")
public class GoodsSearch {

    @Id
    @Column("id")
    private UUID id;

    @Column("track_date")
    private Long trackDate;

    @Column("track_or_customer_id")
    private UUID trackOrCustomerId;

    @Column("keyword")
    private String keyword;

    @Column("status")
    private StatusType status;

    @CreatedDate
    @Column("created_date")
    @JsonIgnore
    private Instant createdDate;

}