package com.sellde.reward.service.model;


import com.sellde.reward.enums.CustomerMonthlyBadge;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.With;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Data
@With
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CustomerMonthlyBadgeModel {

    // private CustomerMonthlyBadge currentMonthBadge;
    private List<SupplierSalesModel> monthlySalesList;

    public CustomerMonthlyBadgeModel() {
        this.monthlySalesList = new ArrayList<>();

    }


    /**
     * NA(last-month) && BRONZE/SILVER(current-month) =>
     * <p>
     * BRONZE(last-month) && NA/SILVER(current-month) =>
     * <p>
     * SILVER(last-month) && NA/BRONZE(current-month) =>
     */
    public void getCurrentMonthBadge() {
//        var thisMonth = LocalDate.now().getMonth().getValue();
//        var lastMonth = LocalDate.now().minusMonths(1).getMonth().getValue();
//        var last2ndMonth = LocalDate.now().minusMonths(2).getMonth().getValue();
//
//        var mapSales = findCurrentMonthlySales();
//        var thisMonthSales = mapSales.get(thisMonth);
//        var lastMonthSales = mapSales.get(lastMonth);
//        var last2ndMonthSales = mapSales.get(last2ndMonth);
//
//        var thisMonthBadge = thisMonthSales.getCustomerBadge();
//        var lastMonthBadge = lastMonthSales.getCustomerBadge();
//
//
//        var currentMonthBadge = CustomerMonthlyBadge.NA;

//        if (previousMonthBadge == CustomerMonthlyBadge.NA) {
//            /* when previous month has no badge */
//            currentMonthBadge = thisMonthBadge;
//        }
//
//
//        if (previousMonthBadge == CustomerMonthlyBadge.BRONZE) {
//            if (thisMonthBadge == CustomerMonthlyBadge.NA) {
//                currentMonthBadge = previousMonthBadge;
//            } else {
//                currentMonthBadge = thisMonthBadge;
//            }
//        }
//
//        if (previousMonthBadge == CustomerMonthlyBadge.SILVER) {
//            if (thisMonthBadge == CustomerMonthlyBadge.NA) {
//                currentMonthBadge = previousMonthBadge;
//            } else if (thisMonthBadge == CustomerMonthlyBadge.BRONZE) {
//                currentMonthBadge = previousMonthBadge;
//            } else {
//                currentMonthBadge = thisMonthBadge;
//            }
//        }


    }

//
//    private HashMap<Integer, SupplierSalesModel> findCurrentMonthlySales() {
//        var currentMonth = LocalDate.now().getMonth().getValue();
//        var map = new HashMap<Integer, SupplierSalesModel>();
//        for (var model : monthlySalesList) {
//            map.put(model.getSalesMonth(), model);
//        }
//
//        if (!map.containsKey(currentMonth)) {
//            var customerId = monthlySalesList.get(0).getCustomerId();
//            var currentYear = LocalDate.now().getYear();
//            map.put(currentMonth, new SupplierSalesModel()
//                    .withSalesMonth(currentMonth)
//                    .withSalesYear(currentYear)
//                    .withCustomerId(customerId));
//        }
//        return map;
//    }
}
