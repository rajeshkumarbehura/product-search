package com.sellde.reward.util;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * Our data base Timezone is SingaporeTime
 */
public class DateTime {

    static String VN_TIME_ZONE = "Asia/Ho_Chi_Minh";


    public static Long currentTimeToMillis() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmsss");
        System.out.println(sdf.format(new Date()));
        return Long.parseLong(sdf.format(new Date()));
    }

    public static Long currentDateAsLong() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHH");
        return Long.parseLong(sdf.format(new Date()));
    }

    public static void main(String[] args) {
        System.out.println(currentTimeToMillis());
    }
}
