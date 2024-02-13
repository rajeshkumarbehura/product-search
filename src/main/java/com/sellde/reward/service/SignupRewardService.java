package com.sellde.reward.service;

import com.sellde.reward.domain.SignupReward;
import com.sellde.reward.enums.StatusType;
import com.sellde.reward.repository.SignupRewardRepository;
import com.sellde.reward.service.mapper.SignupRewardMapper;
import com.sellde.reward.service.model.SignupRewardModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;

@Service
public class SignupRewardService {

    @Autowired
    private SignupRewardRepository signupRewardRepository;

    @Autowired
    private SignupRewardMapper signupRewardMapper;

    public Flux<SignupRewardModel> getAllByEnableStatus() {
        return signupRewardRepository.findAllByStatusOrderByRewardType(StatusType.ENABLE)
                .bufferUntilChanged(SignupReward::getRewardType)
                .map(item -> {
                    var model = signupRewardMapper.toModel(item.get(0));
                    var rangeList = signupRewardMapper.toRangeModelList(item);
                    model.setRangeList(rangeList);
                    return model;
                })
                ;
    }

    public Flux<SignupRewardModel> getAll() {
        return signupRewardRepository.findAllOrderByRewardType()
                .bufferUntilChanged(SignupReward::getRewardType)
                .map(item -> {
                    var model = signupRewardMapper.toModel(item.get(0));
                    var rangeList = signupRewardMapper.toRangeModelList(item);
                    model.setRangeList(rangeList);
                    return model;
                })
                ;
    }

    public Flux<SignupRewardModel> createOrUpdate(List<SignupRewardModel> modelList) {
        var entityList = new ArrayList<SignupReward>();
        modelList.forEach(model -> {
            var rangeModelList = model.getRangeList();
            var entityFromRangeList = signupRewardMapper.toRangeEntityList(rangeModelList);
            entityFromRangeList.forEach(item -> {
                item.setConfigName(model.getConfigName());
                item.setConfigValue(model.getConfigValue());
                item.setRewardType(model.getRewardType());
            });
            entityList.addAll(entityFromRangeList);
        });

        return signupRewardRepository.saveAll(entityList)
                .bufferUntilChanged(SignupReward::getRewardType)
                .map(item -> {
                    var model = signupRewardMapper.toModel(item.get(0));
                    var rangeList = signupRewardMapper.toRangeModelList(item);
                    model.setRangeList(rangeList);
                    return model;
                })
                ;
    }
}

