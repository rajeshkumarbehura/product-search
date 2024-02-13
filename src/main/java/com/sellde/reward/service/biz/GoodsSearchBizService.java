package com.sellde.reward.service.biz;

import com.sellde.reward.domain.GoodsSearch;
import com.sellde.reward.domain.GoodsSearchCount;
import com.sellde.reward.service.GoodsSearchCountService;
import com.sellde.reward.service.GoodsSearchService;
import com.sellde.reward.service.model.GoodsSearchModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

@Service
public class GoodsSearchBizService {

    @Autowired
    private GoodsSearchService goodsSearchService;

    @Autowired
    private GoodsSearchCountService goodsSearchCountService;


    public Mono<GoodsSearchModel.Result> createAndUpdateCount(GoodsSearchModel.Request request) {
        return goodsSearchService.create(request)
                .zipWith(goodsSearchCountService.create(request.getKeyword()))
                .map(tuple -> new GoodsSearchModel.Result(tuple.getT1().getId()));
    }

    public Mono<List<String>> top10Keyword() {
        return goodsSearchCountService.findTop10Keyword()
                .map(GoodsSearchCount::getKeyword)
                .collectList();
    }

    public Mono<List<String>> top10KeywordByCustomerId(UUID customerId) {
        return goodsSearchService.findKeywordsByCustomerId(customerId)
                .map(GoodsSearch::getKeyword)
                .collectList();
    }

}
