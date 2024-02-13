package com.sellde.reward.domain;

import com.sellde.reward.enums.StatusType;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;


@Data
@Table("goods_search_count")
public class GoodsSearchCount {

    @Id
    @Column("id")
    private UUID id;

    @Column("keyword")
    private String keyword;

    @Column("used_count")
    private Long usedCount;

    @Column("status")
    private StatusType status;

}