package com.sellde.utility;

import static com.sellde.reward.enums.CustomerMonthlyBadge.*;

import com.sellde.reward.enums.CustomerMonthlyBadge;
import com.sellde.reward.util.Utils;
import org.junit.jupiter.api.Test;

public class CalculateBadgeTest {

    @Test
    void test() {
        System.out.println("Testing");
    }

    void calculateBadge(CustomerMonthlyBadge thisMonth, CustomerMonthlyBadge lastMonth, CustomerMonthlyBadge last2ndMonth) {

        if (lastMonth == null && last2ndMonth == null) {

        }

    }

    void calculateWhenLast2Month(CustomerMonthlyBadge lastMonth, CustomerMonthlyBadge last2ndMonth) {
        var thisMonth = NA;
        var thisMonthValue = NA.value;
        var last2ndMonthValue = last2ndMonth.value;
        var lastMonthValue = lastMonth.value;


        if (last2ndMonthValue == lastMonthValue) {
            thisMonthValue = lastMonthValue;
        }
        if (last2ndMonthValue > lastMonthValue) {
            thisMonthValue = last2ndMonthValue - 1;
        }
        if (last2ndMonthValue < lastMonthValue) {
            thisMonthValue = lastMonthValue ;
        }

        if (last2ndMonth == SILVER) {
            if (lastMonth == SILVER) {
                thisMonth = SILVER;
            } else if (lastMonth == BRONZE) {
                thisMonth = BRONZE;
            } else {
                thisMonth = BRONZE;
            }
        }
        if (last2ndMonth == BRONZE) {

        }
    }
}
