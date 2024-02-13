package com.sellde.reward.util;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Collection;
import java.util.Objects;

public class Utils {

    private static ObjectMapper objectMapper = new ObjectMapper();

    public static String cleanMobileNo(String mobileNo) {
        if (mobileNo.contains("+")) {
            mobileNo = mobileNo.substring(1);
        }
        if (mobileNo.startsWith("84")) {
            mobileNo = mobileNo.substring(2);
            mobileNo = "0" + mobileNo;
        }
        var mobileNoAsInt = Long.parseLong(mobileNo);
        return "0" + mobileNoAsInt;
    }

    public static boolean isNullOrEmpty(Collection<?> list) {
        if (list == null || list.isEmpty()) {
            return true;
        }
        return false;
    }

    public static boolean isNotEmpty(Collection<?> list) {
        return !isNullOrEmpty(list);
    }

    public static boolean isNotNull(Object obj) {
        return !Objects.isNull(obj);
    }

    public static LocalDate currentLocalDate() {
        return LocalDate.now(ZoneId.of("Asia/Ho_Chi_Minh"));
    }

    public static String emptyString() {
        return "";
    }

    public static int todayYearMonth() {
        var today = LocalDate.now();
        var currentYear = today.getYear();
        var month = today.getMonth().getValue();
        return (currentYear * 100) + month;
    }

    public static int todayYearMonth(LocalDate localDate) {
        var currentYear = localDate.getYear();
        var month = localDate.getMonth().getValue();
        return (currentYear * 100) + month;
    }

    public static String printAsJson(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
