package com.sellde.reward.service.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sellde.reward.enums.SelectOption;
import com.sellde.reward.enums.StatusType;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public class SupplierSalesModel {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class SqsMessage {
        String messageType;
        BasketRequest request;
    }

    @Data
    @With
    @AllArgsConstructor
    @NoArgsConstructor
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class SqsMessageResponse {
        String status;
        String message;
    }

    @Data
    @With
    @AllArgsConstructor
    @FieldDefaults(level = AccessLevel.PRIVATE)
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class BasketRequest {
        UUID customerId;
        UUID basketId;
        int orderNo;
        int salesMonth;
        BigDecimal totalPrice;
        BigDecimal totalRelyBulkPrice;
        BigDecimal discountPrice;
        BigDecimal shippingPrice;
        LocalDate orderDate;
        StatusType status;
        SelectOption selectOption;
        String basketStatus;
        String customerName;
        String preCustomerBadge;
        String postCustomerBadge;
        long phoneNo;

        public BasketRequest() {
            status = StatusType.ENABLE;
            totalPrice = BigDecimal.ZERO;
            discountPrice = BigDecimal.ZERO;
            shippingPrice = BigDecimal.ZERO;
        }
    }

    @Data
    @NoArgsConstructor
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class BasketResponse extends BasketRequest {
        UUID id;

        public UUID getSupplierSalesId() {
            return getId();
        }
    }


    @Data
    @With
    @AllArgsConstructor
    @NoArgsConstructor
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class CustomerBadgeRequest {
        UUID customerId;
        int basketOrderNo;
        String postCustomerBadge;
        Integer yearMonth;
        BigDecimal totalPrice;
        BigDecimal totalRelyBulkPrice;
        BigDecimal totalDiscountPrice;
        BigDecimal totalShippingPrice;
    }


    @Data
    @With
    @AllArgsConstructor
    @NoArgsConstructor
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class SumOfBasketSales {
        UUID customerId;
        Integer yearMonth;
        BigDecimal totalPrice;
        BigDecimal totalDiscountPrice;
        BigDecimal totalShippingPrice;
        BigDecimal totalRelyBulkPrice;
    }
}
