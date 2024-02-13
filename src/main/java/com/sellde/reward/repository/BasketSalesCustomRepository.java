package com.sellde.reward.repository;

import com.sellde.reward.service.model.SupplierSalesModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.UUID;


@Repository
public class BasketSalesCustomRepository {

    @Autowired
    private DatabaseClient databaseClient;

    public Mono<SupplierSalesModel.SumOfBasketSales> getSumOfSalesOfCustomer(UUID customerId, int salesYear, int salesMonth) {
        var sql = """
                select coalesce(sum(ss.total_price), 0)    as total_price,
                       coalesce(sum(ss.total_rely_bulk_price) ,0 )as total_rely_bulk_price,
                       coalesce(sum(ss.discount_price), 0) as total_discount_price,
                       coalesce(sum(ss.shipping_price), 0) as total_shipping_price
                from basket_sales ss
                where ss.customer_id = :customerId
                  and EXTRACT(YEAR FROM ss.order_date) = :salesYear
                  and EXTRACT(MONTH FROM ss.order_date) = :salesMonth
                  and ss.basket_status not in ('ORDER_PLACED','ORDER_CANCEL','ORDER_REJECT','ORDER_INVALID')
                """;

        return databaseClient
                .sql(sql)
                .bind("customerId", customerId)
                .bind("salesYear", salesYear)
                .bind("salesMonth", salesMonth)
                .map((row, rowMetaData) ->
                        new SupplierSalesModel.SumOfBasketSales()
                                .withCustomerId(customerId)
                                .withTotalPrice(row.get("total_price", BigDecimal.class))
                                .withTotalDiscountPrice(row.get("total_discount_price", BigDecimal.class))
                                .withTotalShippingPrice(row.get("total_shipping_price", BigDecimal.class))
                                .withTotalRelyBulkPrice(row.get("total_rely_bulk_price", BigDecimal.class))
                                .withYearMonth(Integer.getInteger(salesYear + "" + salesMonth))
                )
                .first()
                ;
    }
}
