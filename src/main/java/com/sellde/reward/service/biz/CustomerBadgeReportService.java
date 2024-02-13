package com.sellde.reward.service.biz;

import com.sellde.reward.service.CustomerBadgeService;
import com.sellde.reward.service.UserProfileCopyService;
import com.sellde.reward.service.model.CustomerBadgeModel;
import com.sellde.reward.service.model.CustomerBadgeReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.Arrays;

@Service
@Transactional(readOnly = true)
public class CustomerBadgeReportService {

    @Autowired
    UserProfileCopyService userProfileCopyService;

    @Autowired
    private CustomerBadgeService customerBadgeService;

    public Flux<CustomerBadgeReport> createReport() {

        var today = LocalDate.now();
        var currentYearMonth = (today.getYear() * 100) + today.getMonthValue();
        var currentMonthName = today.getMonth().name();

        var lastMonthDate = today.minusMonths(1);
        var lastYearMonth = (lastMonthDate.getYear() * 100) + lastMonthDate.getMonthValue();
        var lastMonthName = lastMonthDate.getMonth().name();

        var nextMonthDate = today.plusMonths(1);
        var nextYearMonth = (nextMonthDate.getYear() * 100) + nextMonthDate.getMonthValue();
        var nextMonthName = nextMonthDate.getMonth().name();

        var yearMonthList = Arrays.asList(lastYearMonth, currentYearMonth, nextYearMonth);
        /* find all unique customerId for last-current-next month */
        var value = customerBadgeService.getAllCustomerIdByYearMonth(yearMonthList)
                .bufferUntilChanged(CustomerBadgeModel::getCustomerId)
                .map(itemList -> {
                    final var report = new CustomerBadgeReport()
                            .withCurrentMonth(currentYearMonth)
                            .withCurrentMonthName(currentMonthName)
                            .withLastMonth(lastYearMonth)
                            .withLastMonthName(lastMonthName)
                            .withNextMonth(nextYearMonth)
                            .withNextMonthName(nextMonthName);

                    itemList.forEach(item -> {
                        var customerId = item.getCustomerId();
                        report.setCustomerId(customerId);
                        if (item.getYearMonth() == lastYearMonth) {
                            report.setLastMonthBadge(item);
                        } else if (item.getYearMonth() == currentYearMonth) {
                            report.setCurrentMonthBadge(item);
                        } else {
                            report.setNextMonthBadge(item);
                        }
                    });
                    return report;
                })
                .flatMap(this::updateCustomer);
        return value;
    }

   private Mono<CustomerBadgeReport> updateCustomer(CustomerBadgeReport report) {
        return userProfileCopyService.getByUserId(report.getCustomerId())
                .map(user -> {
                    report.setCustomerName(user.getUserName());
                    report.setPhoneNo(user.getPhoneNo());
                    return report;
                });
    }
}
