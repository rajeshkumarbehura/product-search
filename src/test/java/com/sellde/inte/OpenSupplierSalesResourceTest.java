package com.sellde.inte;

import com.sellde.reward.domain.CustomerBadge;
import com.sellde.reward.domain.SupplierSales;
import com.sellde.reward.domain.UserProfileCopy;
import com.sellde.reward.enums.SelectOption;
import com.sellde.reward.enums.StatusType;
import com.sellde.reward.service.UserProfileCopyService;
import com.sellde.reward.service.biz.SupplierSalesBizService;
import com.sellde.reward.service.model.SupplierSalesModel;
import com.sellde.reward.service.userproduct.UserProductHttpClient;

import static com.sellde.utility.DomainUtil.*;

import com.sellde.utility.TestUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.List;


import static org.assertj.core.api.Assertions.assertThat;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class OpenSupplierSalesResourceTest extends IntegrationTest {

    @Value("${sellde.supply-reward-api-key}")
    private String supplyRewardApiKey;

    @Autowired
    private SupplierSalesBizService supplierSalesBizService;

    @Test
    void testCreateOrUpdateBasket() {
        var basketRequest = newBasketRequestModel();
        var sqsMessage = new SupplierSalesModel.SqsMessage("BASKET", basketRequest);
        System.out.println("Request -> " + printAsJson(basketRequest));

        var responseModel = webTestClient
                .post()
                .uri("v1/open/supplier-sales")
                .header("supply-reward-api-key", supplyRewardApiKey)
                .body(Mono.just(sqsMessage), SupplierSalesModel.SqsMessage.class)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().valueEquals("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .expectBody(HashMap.class)
                .returnResult().getResponseBody();
        System.out.println("Response -> " + printAsJson(responseModel));

        // validate basket sales
        var condition = new SupplierSales();
        condition.setCustomerId(basketRequest.getCustomerId());
        var savedDomain = supplierSalesRepository.findOne(Example.of(condition)).block();
        assert savedDomain != null;
        assertSavedBasket(savedDomain, basketRequest);

        // validate user copy created
        var userVar = new UserProfileCopy();
        userVar.setUserId(basketRequest.getCustomerId());
        var savedUser = userProfileCopyRepository.findOne(Example.of(userVar)).block();
        assert savedUser != null;
        assertThat(savedUser.getId()).isNotNull();
        assertThat(savedUser.getUserId()).isEqualTo(basketRequest.getCustomerId());

        // validate customer badge
        var badgeVar = new CustomerBadge();
        badgeVar.setCustomerId(basketRequest.getCustomerId());
        var savedCustomerBadgeList = customerBadgeRepository.findAll(Example.of(badgeVar), Sort.by(Sort.Direction.ASC, "yearMonth"))
                .collectList().block();
        assert savedCustomerBadgeList != null;
        System.out.println(printAsJson(savedCustomerBadgeList));
        assertCustomerBadge(savedCustomerBadgeList, basketRequest);

    }

    void assertCustomerBadge(List<CustomerBadge> savedBadgeList, SupplierSalesModel.BasketRequest request) {
        var badge1 = savedBadgeList.get(0);
        assertThat(badge1.getId()).isNotNull();
        assertThat(badge1.getCustomerId()).isEqualTo(request.getCustomerId());
        assertThat(badge1.getYearMonth()).isEqualTo(202209);
        assertThat(badge1.getTotalPrice().longValue()).isEqualTo(request.getTotalPrice().longValue());
        assertThat(badge1.getTotalRelyBulkPrice().longValue()).isEqualTo(request.getTotalRelyBulkPrice().longValue());
        assertThat(badge1.getDefaultBadgeId()).isNotNull();
        if (badge1.getNote().equals("STAFF")) {
            assertThat(badge1.getDefaultBadge()).isEqualTo("BRONZE");
        } else {
            assertThat(badge1.getDefaultBadge()).isEqualTo("NA");
        }
        assertThat(badge1.getMonthlyBadgeId()).isNotNull();
        assertThat(request.getPostCustomerBadge()).isEqualTo("SILVER");
        assertThat(badge1.getMonthlyBadge()).isEqualTo("SILVER");
        assertThat(badge1.getStatus()).isEqualTo(StatusType.ENABLE);

        var badge2 = savedBadgeList.get(1);
        assertThat(badge2.getId()).isNotNull();
        assertThat(badge2.getCustomerId()).isEqualTo(request.getCustomerId());
        assertThat(badge2.getYearMonth()).isEqualTo(202210);
        assertThat(badge2.getTotalPrice().longValue()).isEqualTo(0);
        assertThat(badge2.getTotalRelyBulkPrice().longValue()).isEqualTo(0);
        assertThat(badge2.getDefaultBadgeId()).isNotNull();
        assertThat(badge2.getDefaultBadge()).isEqualTo("SILVER");
        assertThat(badge2.getMonthlyBadgeId()).isNotNull();
        assertThat(badge2.getMonthlyBadge()).isEqualTo("NA");
        assertThat(badge2.getStatus()).isEqualTo(StatusType.ENABLE);

        var badge3 = savedBadgeList.get(2);
        assertThat(badge3.getId()).isNotNull();
        assertThat(badge3.getCustomerId()).isEqualTo(request.getCustomerId());
        assertThat(badge3.getYearMonth()).isEqualTo(202211);
        assertThat(badge3.getTotalPrice().longValue()).isEqualTo(0);
        assertThat(badge3.getTotalRelyBulkPrice().longValue()).isEqualTo(0);
        assertThat(badge3.getDefaultBadgeId()).isNotNull();
        assertThat(badge3.getDefaultBadge()).isEqualTo("BRONZE");
        assertThat(badge3.getMonthlyBadgeId()).isNotNull();
        assertThat(badge3.getMonthlyBadge()).isEqualTo("NA");
        assertThat(badge3.getStatus()).isEqualTo(StatusType.ENABLE);

        var badge4 = savedBadgeList.get(3);
        assertThat(badge4.getId()).isNotNull();
        assertThat(badge4.getCustomerId()).isEqualTo(request.getCustomerId());
        assertThat(badge4.getYearMonth()).isEqualTo(202212);
        assertThat(badge4.getTotalPrice().longValue()).isEqualTo(0);
        assertThat(badge4.getTotalRelyBulkPrice().longValue()).isEqualTo(0);
        assertThat(badge4.getDefaultBadgeId()).isNotNull();
        assertThat(badge4.getDefaultBadge()).isEqualTo("MEMBER");
        assertThat(badge4.getMonthlyBadgeId()).isNotNull();
        assertThat(badge4.getMonthlyBadge()).isEqualTo("NA");
        assertThat(badge4.getStatus()).isEqualTo(StatusType.ENABLE);

    }

    void assertSavedBasket(SupplierSales saved, SupplierSalesModel.BasketRequest request) {
        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getCustomerId()).isEqualTo(request.getCustomerId());
        assertThat(saved.getBasketId()).isEqualTo(request.getBasketId());
        assertThat(saved.getOrderDate()).isEqualTo(request.getOrderDate());
        assertThat(saved.getOrderNo()).isEqualTo(request.getOrderNo());
        assertThat(saved.getTotalPrice().longValue()).isEqualTo(request.getTotalPrice().longValue());
        assertThat(saved.getTotalRelyBulkPrice().doubleValue()).isEqualTo(request.getTotalRelyBulkPrice().longValue());
        assertThat(saved.getPreCustomerBadge()).isEqualTo(request.getPreCustomerBadge());
        assertThat(saved.getPostCustomerBadge()).isEqualTo(request.getPostCustomerBadge());
        assertThat(saved.getBasketStatus()).isEqualTo(request.getBasketStatus());
    }


    @Test
    void testCreateOrUpdateBasket_whenForBuyer() {
        var basketRequest = newBasketRequestModel();
        basketRequest.setSelectOption(SelectOption.FOR_BUYER);
        var sqsMessage = new SupplierSalesModel.SqsMessage("BASKET", basketRequest);
        System.out.println("Request -> " + printAsJson(basketRequest));

        var responseModel = webTestClient
                .post()
                .uri("v1/open/supplier-sales")
                .header("supply-reward-api-key", supplyRewardApiKey)
//                .bodyValue(sqsMessage)
                .body(Mono.just(sqsMessage), SupplierSalesModel.SqsMessage.class)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().valueEquals("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .expectBody(HashMap.class)
                .returnResult().getResponseBody();
        System.out.println("Response -> " + printAsJson(responseModel));
    }

    @Test
    void testCreateOrUpdateBasket_WithoutApiKey() {
        var basketRequest = newBasketRequestModel();

        var sqsMessage = new SupplierSalesModel.SqsMessage("BASKET", basketRequest);

        System.out.println(printAsJson(basketRequest));

        var responseModel = webTestClient
                .post()
                .uri("v1/open/supplier-sales")
                .bodyValue(sqsMessage)
                .exchange()
                .expectStatus().isUnauthorized()
                .expectHeader().valueEquals("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .expectBody(HashMap.class)
                .returnResult().getResponseBody();

        System.out.println("Response -> " + printAsJson(responseModel));
    }

    @Test
    void testCreateOrUpdateBasket_WhenSameOrderMultiRequest() {
        var basketRequest = newBasketRequestModel();
        var sqsMessage = new SupplierSalesModel.SqsMessage("BASKET", basketRequest);
        System.out.println("Request -> " + printAsJson(basketRequest));

        var responseModel = webTestClient
                .post()
                .uri("v1/open/supplier-sales")
                .header("supply-reward-api-key", supplyRewardApiKey)
                .bodyValue(sqsMessage)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().valueEquals("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .expectBody(HashMap.class)
                .returnResult().getResponseBody();

        System.out.println("Response -> " + printAsJson(responseModel));


        var basketRequest2 = newBasketRequestModel2();
        var sqsMessage2 = new SupplierSalesModel.SqsMessage("BASKET", basketRequest2);
        var responseModel2 = webTestClient
                .post()
                .uri("v1/open/supplier-sales")
                .header("supply-reward-api-key", supplyRewardApiKey)
                .bodyValue(sqsMessage2)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().valueEquals("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .expectBody(HashMap.class)
                .returnResult().getResponseBody();
        System.out.println("Response -> " + printAsJson(responseModel2));
    }

    @Test
    void testCreateOrUpdateBasket_WhenMultiOrderSameMonth() {
        var basketRequest = newBasketRequestModel();
        var sqsMessage = new SupplierSalesModel.SqsMessage("BASKET", basketRequest);
        System.out.println("Request -> " + printAsJson(basketRequest));

        var responseModel = webTestClient
                .post()
                .uri("v1/open/supplier-sales")
                .header("supply-reward-api-key", supplyRewardApiKey)
                .bodyValue(sqsMessage)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().valueEquals("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .expectBody(HashMap.class)
                .returnResult().getResponseBody();

        System.out.println("Response -> " + printAsJson(responseModel));


        var sqsMessage3 = new SupplierSalesModel.SqsMessage("BASKET", newBasketRequestModel3());
        var responseModel3 = webTestClient
                .post()
                .uri("v1/open/supplier-sales")
                .header("supply-reward-api-key", supplyRewardApiKey)
                .bodyValue(sqsMessage3)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().valueEquals("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .expectBody(HashMap.class)
                .returnResult().getResponseBody();
        System.out.println("Response -> " + printAsJson(responseModel3));
    }

    SupplierSalesModel.BasketRequest newBasketRequestModel2() {
        return TestUtil.readJsonFile("json/basket/order-100-basket2.json", SupplierSalesModel.BasketRequest.class);
    }

    SupplierSalesModel.BasketRequest newBasketRequestModel3() {
        return TestUtil.readJsonFile("json/basket/order-102-basket.json", SupplierSalesModel.BasketRequest.class);
    }


    @Autowired
    UserProfileCopyService userProfileCopyService;

    @Autowired
    UserProductHttpClient userHttpclient;

//    @Test
//    void testUserCopy(){
//        var value = UUID.fromString("4d61f984-667b-418f-865a-818e85a7085c");
////       userProfileCopyService.createFromUserId(value).block();
//
//        var value2 = userHttpclient.get(ApiPath.USER_GET_CUSTOMER, UserModel.Request.class, value)
//                        .block();
//
//        System.out.println("****** "+value2);
//
//    }

}