package com.sellde.reward.enums;

public enum StaffBadge {

    NA_BADGE("NA"),
    DEFAULT_BADGE("BRONZE"),
    DEFAULT_BADGE_ID("4e30ecd6-d0f9-4115-bf1e-5d690d19fc83");

    private final String value;

    StaffBadge(String value) {
        this.value=value;
    }

    public String getValue(){
        return this.value;
    }
}
