package com.sellde.reward.service.model;

import com.sellde.reward.enums.StatusType;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

public class UserModel {

    @Data
    @With
    @AllArgsConstructor
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class Request {
        UUID id;
        UUID userId;
        long mobileNo;
        String name;
        String type;
        String subgroup;
        StatusType status;

        public Request() {
            status = StatusType.ENABLE;
        }
    }

    @Data
    @With
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ProfileModel {
        private UUID id;
        private UUID userId;
        private String userName;
        private long phoneNo;
        private String userType;
        private String subgroup;
        private StatusType status;
    }


}
