package com.sellde.reward.service.biz;


import com.sellde.reward.service.CustomerBadgeService;
import com.sellde.reward.service.SupplierSalesService;
import com.sellde.reward.service.UserProfileCopyService;
import com.sellde.reward.service.model.CustomerBadgeModel;
import com.sellde.reward.service.model.SupplierSalesModel;
import com.sellde.reward.util.Utils;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@Transactional
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SupplierSalesBizService {

    @Autowired
    SupplierSalesService supplierSalesService;

    @Autowired
    CustomerBadgeService customerBadgeService;

    @Autowired
    UserProfileCopyService userProfileCopyService;


    public Mono<List<CustomerBadgeModel>> processBasketAndUpdateCustomerBadge(SupplierSalesModel.BasketRequest request) {
        return userProfileCopyService.createFromUserId(request.getCustomerId(), request.getCustomerName(), request.getPhoneNo()) /* create user id in user_copy if it's new */
                /* create or update the basket */
                .then(supplierSalesService.createFromBasket(request))
                /* get total of today for customer */
                .zipWhen(item -> supplierSalesService.getSumOfBasketSalesByCustomer(item))
                .map(tuple -> {
                    var basketResponse = tuple.getT1();
                    var sumOfSales = tuple.getT2();
                    var orderDate = basketResponse.getOrderDate();
                    return new SupplierSalesModel.CustomerBadgeRequest()
                            .withCustomerId(request.getCustomerId())
                            .withYearMonth(Utils.todayYearMonth(orderDate))
                            .withTotalPrice(sumOfSales.getTotalPrice())
                            .withTotalRelyBulkPrice(sumOfSales.getTotalRelyBulkPrice())
                            .withTotalDiscountPrice(sumOfSales.getTotalDiscountPrice())
                            .withTotalShippingPrice(sumOfSales.getTotalShippingPrice())
                            .withBasketOrderNo(request.getOrderNo())
                            .withPostCustomerBadge(request.getPostCustomerBadge())
                            ;
                })
                /* update customer badge based on basket PostCustomerBadge */
                .flatMap(customerBadgeService::createOrUpdateWhenBasketSalesUpdate);
    }
}
