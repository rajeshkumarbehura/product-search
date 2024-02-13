package com.sellde.reward.service;

import com.sellde.reward.domain.CustomerBadge;
import com.sellde.reward.enums.StaffBadge;
import com.sellde.reward.enums.StatusType;
import com.sellde.reward.repository.CustomerBadgeRepository;
import com.sellde.reward.service.mapper.CustomerBadgeMapper;
import com.sellde.reward.service.model.CustomerBadgeModel;
import com.sellde.reward.service.model.MonthlyBadgeModel;
import com.sellde.reward.service.model.SupplierSalesModel;
import com.sellde.reward.util.Constant;
import com.sellde.reward.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@Service
public class CustomerBadgeService {

    @Autowired
    private CustomerBadgeRepository customerBadgeRepository;

    @Autowired
    private CustomerBadgeMapper customerBadgeMapper;

    @Autowired
    private MonthlyBadgeService monthlyBadgeService;

    @Autowired
    private SupplierSalesService supplierSalesService;

    @Autowired
    private UserProfileCopyService userProfileCopyService;

    private final int fixedNextMonths = 3;

    /**
     * call this method when new Basket Sales get updated or created.
     * The customer badge created based on Basket Sales & PostCustomerBadge after basket confirmed or cancelled
     */
    public Mono<List<CustomerBadgeModel>> createOrUpdateWhenBasketSalesUpdate(SupplierSalesModel.CustomerBadgeRequest badgeRequest) {

        var customerId = badgeRequest.getCustomerId();
        var yearMonth = badgeRequest.getYearMonth();
        return customerBadgeRepository
                /* find any existing customer badge for current month */
                .findOneByCustomerIdAndYearMonth(customerId, yearMonth)
                .map(existingItem -> {
                    // ignore badges, it will update next step.
                    existingItem.setTotalPrice(badgeRequest.getTotalPrice());
                    existingItem.setTotalRelyBulkPrice(badgeRequest.getTotalRelyBulkPrice());
                    existingItem.setTotalDiscountPrice(badgeRequest.getTotalDiscountPrice());
                    existingItem.setTotalShippingPrice(badgeRequest.getTotalShippingPrice());
                    return existingItem;
                })
                /* when default, create badge with default values */
                .defaultIfEmpty(customerBadgeMapper.toEntityFromCustomerBadgeRequest(badgeRequest))
                // update the badge before saving
                .flatMap(this::evaluateBadgeOnNewBasketConfirmOrCancel)
                .flatMap(customerBadgeRepository::save)
                .map(customerBadgeMapper::toModel)
                .zipWhen(item -> createNextMonthWhenCustomerGetsBadge(item).collectList())
                .map(tuple -> {
                    var list = tuple.getT2();
                    list.add(tuple.getT1());
                    return list;
                });
    }


    /**
     * Create/Update next month DEFAULT_BADGE, when customer qualify for the badge for current month.
     * <p>
     * Current Month's monthly badge = Next month's Fix/Default badge
     */
    private Flux<CustomerBadgeModel> createNextMonthWhenCustomerGetsBadge(CustomerBadgeModel currentMonthBadgeModel) {
        var currentDate = LocalDate.parse(currentMonthBadgeModel.getYearMonth() + "01",
                DateTimeFormatter.ofPattern("yyyyMMdd"));

        var customerId = currentMonthBadgeModel.getCustomerId();
        var nextYearMonthList = createNextYearMonthsExcludeCurrentMonth(currentDate);
        var value = customerBadgeRepository
                .findAllByCustomerIdAndYearMonthIsIn(customerId, nextYearMonthList)
                .collectList()
                .zipWith(monthlyBadgeService.getAllEnabled().collectList())
                .map(tuple -> {
                    var existingBadgeList = tuple.getT1();
                    var monthlyBadgeList = tuple.getT2();
                    return createOrUpdateCustomerBadgeModelList(existingBadgeList, monthlyBadgeList, currentMonthBadgeModel, currentDate);
                })
                .flatMapMany(customerBadgeRepository::saveAll)
                .map(customerBadgeMapper::toModel);
        return value;
    }

    private Mono<CustomerBadge> evaluateBadgeOnNewBasketConfirmOrCancel(CustomerBadge customerBadge) {
        if (customerBadge.getDefaultBadge() == null) {
            /*  if default badge is null then set to NA */
            customerBadge.setDefaultBadge(StaffBadge.NA_BADGE.getValue());
        }
        return supplierSalesService.getMaxOrderNoForCustomer(customerBadge.getCustomerId())
                .zipWith(userProfileCopyService.isStaff(customerBadge.getCustomerId()))
                .map(tuple2 -> {
                    var isStaff = tuple2.getT2();
                    /* when customer is staff of sellde & Default bade is null or NA */
                    if (isStaff && customerBadge.getDefaultBadge().equals(StaffBadge.NA_BADGE.getValue())) {
                        customerBadge.setDefaultBadgeId(UUID.fromString(StaffBadge.DEFAULT_BADGE_ID.getValue()));
                        customerBadge.setDefaultBadge(StaffBadge.DEFAULT_BADGE.getValue());
                        customerBadge.setNote("STAFF");
                    }
                    var basketWithMaxOrderNo = tuple2.getT1();
                    customerBadge.setMonthlyBadge(basketWithMaxOrderNo.getPostCustomerBadge());
                    customerBadge.setMonthlyBadgeId(null);
                    return customerBadge;
                })
                .zipWhen(updatedCustomerBadge -> monthlyBadgeService.getAllEnabledIntoMap())
                .map(tuple -> {
                    var badge = tuple.getT1();
                    var monthlyBadgeMap = tuple.getT2();
                    var monthlyBadge = monthlyBadgeMap.get(badge.getMonthlyBadge());
                    var defaultBadge = monthlyBadgeMap.get(badge.getDefaultBadge());
                    badge.setMonthlyBadgeId(monthlyBadge.getId());
                    badge.setDefaultBadgeId(defaultBadge.getId());

                    /* update status to ENABLE */
                    badge.setStatus(StatusType.ENABLE);
                    return badge;
                });
    }

    public Mono<CustomerBadgeModel> getByCustomerIdAndCurrentMonth(UUID customerId) {
        var today = LocalDate.now();
        var currentYear = today.getYear();
        var month = today.getMonth().getValue();
        var yearMonth = (currentYear * 100) + month;
        return customerBadgeRepository
                .findOneByCustomerIdAndYearMonth(customerId, yearMonth)
                .map(customerBadgeMapper::toModel)
                .zipWith(monthlyBadgeService.getAllEnabled().collectList())
                .map(tuple -> {
                    var monthlyBadgeList = tuple.getT2();
                    var model = tuple.getT1();
                    return evaluateFinalBadge(model, monthlyBadgeList);
                })
                .defaultIfEmpty(new CustomerBadgeModel()
                        .withCustomerId(customerId)
                        .withYearMonth(yearMonth))
                .zipWith(monthlyBadgeService.getAllEnabled().collectList())
                .map(tuple -> {
                    var badgeModel = tuple.getT1();
                    return badgeModel.withCustomerBadgeRangeList(tuple.getT2());
                });
    }

    /**
     * Create Next months Badge for customer depend on Badge.
     *
     * @param existingBadgeList
     * @param currentBadge
     * @param currentDate
     * @return
     */
    private List<CustomerBadge> createOrUpdateCustomerBadgeModelList(List<CustomerBadge> existingBadgeList,
                                                                     List<MonthlyBadgeModel> monthlyBadgeList,
                                                                     CustomerBadgeModel currentBadge, LocalDate currentDate) {

        var existingMap = new HashMap<Integer, CustomerBadge>();
        existingBadgeList.forEach(item -> {
            existingMap.put(item.getYearMonth(), item);
        });

        var indexBadgeMap = new HashMap<String, Integer>();
        var badgeIndexMap = new HashMap<Integer, String>();
        var badgeIdIndexMap = new HashMap<Integer, UUID>();
        UUID monthlyBadgeIdOfNA = null;

        for (var item : monthlyBadgeList) {
            indexBadgeMap.put(item.getBadge(), item.getBadgeIndex());
            badgeIndexMap.put(item.getBadgeIndex(), item.getBadge());
            badgeIdIndexMap.put(item.getBadgeIndex(), item.getMonthlyBadgeId()); /* index to monthly Badge Id mapping*/
            if (Constant.NOT_AVIALBLE.equalsIgnoreCase(item.getBadge())) {
                monthlyBadgeIdOfNA = item.getMonthlyBadgeId();
            }
        }

        var badgeIndex = 0;
        var modelList = new ArrayList<CustomerBadge>();
        int monthCount = 1;

        for (var count = 0; count <= fixedNextMonths; count++) {
            badgeIndex = indexBadgeMap.get(currentBadge.getMonthlyBadge()) - count;

            // default badge for next month must be match as current month
            var newDefaultBadge = badgeIndexMap.get(badgeIndex);
            var newDefaultBadgeId = badgeIdIndexMap.get(badgeIndex);
            if (newDefaultBadge == null) {
                /* when any default badge is null, then set NA */
                newDefaultBadge = Constant.NOT_AVIALBLE;
                newDefaultBadgeId = monthlyBadgeIdOfNA;
            }
            var newDefaultBadgeIndex = badgeIndex;
            var nextYearMonth = Utils.todayYearMonth(currentDate.plusMonths(monthCount));

            CustomerBadge customerBadge = null;
            if (existingMap.containsKey(nextYearMonth)) {
                customerBadge = existingMap.get(nextYearMonth);
                customerBadge.setLastModifiedDate(Instant.now());
            } else {
                // new when does not find any existing one
                customerBadge = new CustomerBadge();
                customerBadge.setCreatedDate(Instant.now());
                customerBadge.setLastModifiedDate(Instant.now());
            }

            customerBadge.setCustomerId(currentBadge.getCustomerId());
            customerBadge.setYearMonth(nextYearMonth);
            /* set with default badge */
            customerBadge.setDefaultBadgeId(newDefaultBadgeId);
            customerBadge.setDefaultBadge(newDefaultBadge);
            customerBadge.setMonthlyBadgeId(monthlyBadgeIdOfNA); /* default to NA */
            customerBadge.setMonthlyBadge(Constant.NOT_AVIALBLE);/* default to NA */
            customerBadge.setStatus(StatusType.ENABLE);
            customerBadge.setNote(currentBadge.getNote());

            modelList.add(customerBadge);
            monthCount++;
        }
        return modelList;
    }

    private List<Integer> createNextYearMonthsExcludeCurrentMonth(LocalDate currentDate) {
        var yearMonthList = new ArrayList<Integer>();
        for (var nextMonthCount = 1; nextMonthCount <= fixedNextMonths + 1; nextMonthCount++) {
            var nextYearMonth = Utils.todayYearMonth(currentDate.plusMonths(nextMonthCount));
            yearMonthList.add(nextYearMonth);
        }
        return yearMonthList;
    }

    public Flux<CustomerBadgeModel> getAllCustomerIdByYearMonth(List<Integer> yearMonthList) {
        var sort = Sort.by(Sort.Direction.ASC, "customerId", "yearMonth");

        var modelListMono = customerBadgeRepository.findAllByYearMonthIsIn(yearMonthList, sort)
                .map(customerBadgeMapper::toModel).collectList();

        var value = monthlyBadgeService.getAllEnabled().collectList()
                .zipWith(modelListMono)
                .map(tuple -> {
                    var monthlyBadgeList = tuple.getT1();
                    var custModelList = tuple.getT2();
                    custModelList.forEach(item -> {
                        evaluateFinalBadge(item, monthlyBadgeList);
                    });
                    return custModelList;
                })
                .flatMapMany(Flux::fromIterable);
        return value;
    }

    private CustomerBadgeModel evaluateFinalBadge(CustomerBadgeModel badgeModel, List<MonthlyBadgeModel> monthlyBadgeList) {

        var indexBadgeMap = new HashMap<String, Integer>();
        monthlyBadgeList.forEach(item -> {
            indexBadgeMap.put(item.getBadge(), item.getBadgeIndex());
        });

        var defaultBadge = badgeModel.getDefaultBadge();
        var defaultBadgeIndex = indexBadgeMap.get(badgeModel.getDefaultBadge());
        var monthlyBadge = badgeModel.getMonthlyBadge();
        var monthlyBadgeIndex = indexBadgeMap.get(badgeModel.getMonthlyBadge());

        if (defaultBadgeIndex > monthlyBadgeIndex) {
            badgeModel.setFinalBadge(defaultBadge);
        } else {
            badgeModel.setFinalBadge(monthlyBadge);
        }
        return badgeModel;
    }

}
