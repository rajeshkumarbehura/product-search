package com.sellde.reward.service.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

public class GoodsSearchModel {

    @Data
    public static class Request {
        private UUID trackOrCustomerId;
        private String keyword;
    }

    @Data
    @AllArgsConstructor
    public static class Result {
        private UUID id;
    }
}
