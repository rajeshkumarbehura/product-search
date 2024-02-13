package com.sellde.reward.repository;

import com.sellde.reward.domain.SupplierSales;
import com.sellde.reward.enums.SelectOption;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.UUID;


@Repository
public interface SupplierSalesRepository extends R2dbcRepository<SupplierSales, UUID> {

    Mono<SupplierSales> findByOrderNo(Integer orderNo);

    @Query("""
            select * from basket_sales
            where order_no =
              (
                  select max(order_no)
                  from basket_sales bs
                  where bs.customer_id = :customerId
                  and bs.basket_status not in ('ORDER_PLACED', 'ORDER_CANCEL', 'ORDER_REJECT', 'ORDER_INVALID')
              )
            """)
    Mono<SupplierSales> findMaxValidOrderForCustomer(UUID customerId);
}
