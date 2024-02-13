package com.sellde.reward.service;

import com.sellde.reward.domain.SignupQnA;
import com.sellde.reward.repository.SignupQnARepository;
import com.sellde.reward.service.mapper.SignupQnAMapper;
import com.sellde.reward.service.model.SignupQnAModel;
import com.sellde.reward.util.Constant;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuples;

import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class SignupQnAService {

    @Autowired
    private SignupQnARepository signupQnARepository;

    @Autowired
    private SignupQnAMapper signupQnAMapper;

    public Flux<SignupQnAModel.GetQuery> getAllDefaultQuery() {
        return signupQnARepository.findAllByPhoneNoOrderByQueryNo(Constant.SYSTEM_PHONE_NO)
                .map(signupQnAMapper::toOnlyQuery);
    }

    public Mono<Boolean> isAnsweredByPhoneNo(long phoneNo) {
        return signupQnARepository.findAllByPhoneNoOrderByQueryNo(Constant.SYSTEM_PHONE_NO)
                .collectList()
                .zipWith(signupQnARepository.findAllByPhoneNoAndAnswerIsNotNullOrderByQueryNo(phoneNo).collectList())
                .map(tuple -> {
                    var selldeQna = tuple.getT1();
                    var custQna = tuple.getT2();
                    if (custQna.isEmpty()) {
                        return false;
                    }
                    return compareAnswer(selldeQna, custQna);
                });
    }

    public Mono<JSONObject> isValidAnswerByCustomer(List<SignupQnAModel.RequestResponse> requestList) {
        var customerPhoneNo = requestList.get(0).getPhoneNo();

        /* first find all if existing anc compare answer & validate time for 24hr */

        var selldeAns = signupQnARepository
                .findAllByPhoneNoOrderByQueryNo(Constant.SYSTEM_PHONE_NO)
                .collectList();
        var existingAnsMono = signupQnARepository
                .findAllByPhoneNoOrderByQueryNo(customerPhoneNo)
                .collectList();

        var result = new JSONObject();
        var value = Mono.zip(selldeAns, existingAnsMono)
                .map(tuple -> {
                    var selldeAnsList = tuple.getT1();
                    var existingAnsList = tuple.getT2();
                    if (tuple.getT2().isEmpty()) {
                        return Tuples.of(selldeAnsList, existingAnsList, true);
                    }
                    var isValidAnswer = compareAnswer(selldeAnsList, existingAnsList);
                    var lastUpdatedTime = (existingAnsList.isEmpty()) ? Instant.now()
                            : existingAnsList.get(0).getLastModifiedDate();
                    var isValidAnswerWithIn24hrs = true;
                    if (Boolean.FALSE == isValidAnswer &&
                            /* hours difference needs to be 24 hrs */
                            (Duration.between(lastUpdatedTime, Instant.now()).toHours() < 24)) {
                        isValidAnswerWithIn24hrs = false;
                    }
                    return Tuples.of(selldeAnsList, existingAnsList, isValidAnswerWithIn24hrs);
                })
                .flatMap(tuple3 -> {
                    var isValidAnswerWithIn24hrs = tuple3.getT3();
                    var lastUpdatedTime = (tuple3.getT2().isEmpty()) ? Instant.now()
                            : tuple3.getT2().get(0).getLastModifiedDate();
                    if (Boolean.FALSE == isValidAnswerWithIn24hrs) {
                        return Mono.just(
                                result.put("isValidSignup", false)
                                        .put("code", "INVALID_DURATION")
                                        .put("message", "User try to login in 24hrs after failed to signup")
                        );
                    } else {
                        return validateAndUpdate(tuple3.getT1(), tuple3.getT2(), requestList);
                    }
                });
        return value;
    }


    private Mono<JSONObject> validateAndUpdate(
            List<SignupQnA> selldeAnsList, List<SignupQnA> userExistingAnsList,
            List<SignupQnAModel.RequestResponse> newRequestList) {

        final var mapExistingUserAns = new HashMap<Integer, SignupQnA>();
        List<SignupQnA> domainList = null;
        if (userExistingAnsList.isEmpty()) {
            domainList = signupQnAMapper.toEntity(newRequestList);
        } else {
            userExistingAnsList.forEach(item -> {
                mapExistingUserAns.put(item.getQueryNo(), item);
            });
            domainList = newRequestList.stream()
                    .map(newItem -> {
                        var qno = newItem.getQueryNo();
                        if (mapExistingUserAns.containsKey(qno)) {
                            return signupQnAMapper.toUpdateEntity(mapExistingUserAns.get(qno), newItem);
                        } else {
                            return signupQnAMapper.toEntity(newItem);
                        }
                    }).collect(Collectors.toList());
        }
        return signupQnARepository.saveAll(domainList)
                .collectList()
                .map(savedItemList -> compareAnswer(selldeAnsList, savedItemList))
                .map(isValid -> new JSONObject().put("isValidSignup", isValid));
    }


    private Boolean compareAnswer(List<SignupQnA> selldeAnsList, List<SignupQnA> userAnsList) {
        var result = new JSONObject();
        if (userAnsList.size() != selldeAnsList.size()) {
            return false;
        }
        var mapUserAns = new HashMap<Integer, String>();
        userAnsList.forEach(item -> {
            mapUserAns.put(item.getQueryNo(), item.getAnswer());
        });
        for (var selldeItem : selldeAnsList) {
            var queryNo = selldeItem.getQueryNo();
            var userAns = mapUserAns.get(queryNo).trim().toLowerCase();
            var selldeAnsValues = Arrays.asList(selldeItem.getAnswer().trim().toLowerCase().split(","));
            if (Boolean.FALSE == (selldeAnsValues.contains(userAns))) {
                result.put("isValidSignup", false)
                        .put("code", "INVALID_SIGNUP_QNA")
                        .put("message", "Invalid answers");
                return false;
            }
        }
        return true;
    }

}
