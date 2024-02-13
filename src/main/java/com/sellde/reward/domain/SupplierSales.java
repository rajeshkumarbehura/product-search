package com.sellde.reward.domain;

import com.sellde.reward.enums.StatusType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Data
@Table("basket_sales")
@EqualsAndHashCode(callSuper = false)
public class SupplierSales extends AuditDomain {

    @Id
    @Column("id")
    private UUID id;

    @Column("customer_id")
    private UUID customerId;

    @Column("basket_id")
    private UUID basketId;

    @Column("order_date")
    private LocalDate orderDate;

    @Column("order_no")
    private Integer orderNo;

    @Column("total_price")
    private BigDecimal totalPrice;

    @Column("total_rely_bulk_price")
    private BigDecimal totalRelyBulkPrice;

    @Column("discount_price")
    private BigDecimal discountPrice;

    @Column("shipping_price")
    private BigDecimal shippingPrice;

    @Column("select_option")
    private String selectOption;

    @Column("pre_customer_badge")
    private String preCustomerBadge;

    @Column("post_customer_badge")
    private String postCustomerBadge;

    @Column("basket_status")
    private String basketStatus;

    @Column("status")
    private StatusType status;
}