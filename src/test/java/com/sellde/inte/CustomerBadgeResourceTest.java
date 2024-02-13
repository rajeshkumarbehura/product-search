package com.sellde.inte;

import com.sellde.reward.service.CustomerBadgeService;
import com.sellde.reward.service.biz.SupplierSalesBizService;
import com.sellde.reward.service.model.CustomerBadgeModel;
import com.sellde.utility.DomainUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import java.time.LocalDate;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CustomerBadgeResourceTest extends IntegrationTest {

    @Autowired
    CustomerBadgeService customerBadgeService;

    @Autowired
    SupplierSalesBizService supplierSalesBizService;

    @Test
    void testGetByParam() {

        cleanDB();

        var basketRequest = DomainUtil.newBasketRequestModel()
                /* set today order date */
                .withOrderDate(LocalDate.now());

        supplierSalesBizService.processBasketAndUpdateCustomerBadge(basketRequest)
                .block();

        var customerId = basketRequest.getCustomerId();

        var responseModel = webTestClient
                .get()
                .uri("/v1/customer-badge?customerId={1}", customerId)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().valueEquals("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .expectBody(CustomerBadgeModel.class)
                .returnResult().getResponseBody();
        System.out.println("Response -> " + printAsJson(responseModel));

        assert responseModel != null;
        assert responseModel.getFinalBadge().equals("SILVER");
        assert responseModel.getTotalPrice().longValue() == 4500000L;
        assert responseModel.getTotalDiscountPrice().longValue() == 40000L;
        assert responseModel.getTotalShippingPrice().longValue() == 15000L;
        assert responseModel.getActualSpentTotalPrice().longValue() == 4525000L;
        assert responseModel.getFinalBadge().equals("SILVER");
        assert responseModel.getCustomerBadgeRangeList().size() != 0;
        assert responseModel.getCustomerId().toString().equals(basketRequest.getCustomerId().toString());
    }

}