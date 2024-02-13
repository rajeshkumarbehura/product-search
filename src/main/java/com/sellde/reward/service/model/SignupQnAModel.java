package com.sellde.reward.service.model;

import lombok.Data;

public class SignupQnAModel {

    @Data
    public static class RequestResponse {
        private long phoneNo;
        private int queryNo;
        private String answer;
    }

    @Data
    public static class GetQuery {
        private int queryNo;
        private String queryEn;
        private String queryVn;
    }
}
