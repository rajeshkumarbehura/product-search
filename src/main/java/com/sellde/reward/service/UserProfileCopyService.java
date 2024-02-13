package com.sellde.reward.service;

import com.sellde.reward.domain.UserProfileCopy;
import com.sellde.reward.repository.UserProfileCopyRepository;
import com.sellde.reward.service.mapper.UserProfileCopyMapper;
import com.sellde.reward.service.model.UserModel;
import com.sellde.reward.service.userproduct.UserProductHttpClient;
import com.sellde.reward.util.ApiPath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Objects;
import java.util.UUID;

@Service
public class UserProfileCopyService {

    @Autowired
    private UserProfileCopyRepository userProfileCopyRepository;

    @Autowired
    private UserProductHttpClient userHttpclient;

    @Autowired
    private UserProfileCopyMapper userProfileCopyMapper;

    public Mono<UserModel.ProfileModel> getByUserId(UUID userId) {
        return userProfileCopyRepository.findOneByUserId(userId)
                .map(userProfileCopyMapper::toModel)
                .defaultIfEmpty(new UserModel.ProfileModel());
    }

    public Mono<UserModel.ProfileModel> createFromUserId(UUID customerId, String customerName, long phoneNo) {
        return userProfileCopyRepository.findOneByUserId(customerId)
                .switchIfEmpty(findUser(customerId, customerName, phoneNo))
                .map(userProfileCopyMapper::toModel);
    }

    private Mono<UserProfileCopy> findUser(UUID customerId, String customerName, long phoneNo) {
        return userHttpclient.get(ApiPath.USER_GET_CUSTOMER, UserModel.Request.class, customerId)
                .map(userProfileCopyMapper::toEntity)
                .map(domain -> {
                    if (domain.getPhoneNo() == phoneNo) {
                        domain.setUserName(customerName);
                    }
                    return domain;
                })
                .flatMap(userProfileCopyRepository::save)
                .onErrorReturn(new UserProfileCopy());
    }

    public Mono<Boolean> isStaff(UUID customerId) {
        return userProfileCopyRepository.findOneByUserId(customerId)
                .map(item -> {
                    return Objects.equals(item.getSubgroup(), "STAFF");
                })
                .defaultIfEmpty(false);
    }

}
