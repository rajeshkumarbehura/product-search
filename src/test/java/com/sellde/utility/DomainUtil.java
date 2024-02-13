package com.sellde.utility;

import com.sellde.reward.service.model.SupplierSalesModel;

public class DomainUtil {

    public static  SupplierSalesModel.BasketRequest newBasketRequestModel() {
        return TestUtil.readJsonFile("json/basket/order-100-basket.json", SupplierSalesModel.BasketRequest.class);
    }

    public static long randomMobileNo() {
        return (long) (Math.random() * 1000000000);
    }
}
