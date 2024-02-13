package com.sellde.reward.service;

import com.sellde.reward.domain.GoodsSearch;
import com.sellde.reward.enums.StatusType;
import com.sellde.reward.repository.GoodsSearchRepository;
import com.sellde.reward.service.mapper.GoodsSearchMapper;
import com.sellde.reward.service.model.GoodsSearchModel;
import com.sellde.reward.util.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

@Service
public class GoodsSearchService {

    @Autowired
    private GoodsSearchRepository goodsSearchRepository;

    @Autowired
    private GoodsSearchMapper goodsSearchMapper;


    public Mono<GoodsSearchModel.Result> create(GoodsSearchModel.Request request) {
        var newDomain = goodsSearchMapper.fromRequest(request);
        newDomain.setTrackDate(DateTime.currentDateAsLong());
        newDomain.setStatus(StatusType.ENABLE);
        return goodsSearchRepository.findOne(Example.of(newDomain))
                .switchIfEmpty(goodsSearchRepository.save(newDomain))
                .map(item -> new GoodsSearchModel.Result(item.getId()));
    }


    public Flux<GoodsSearch> findKeywordsByCustomerId(UUID customerId) {
        return goodsSearchRepository.findTopKeywordsByCustomerId(customerId);
    }

}
