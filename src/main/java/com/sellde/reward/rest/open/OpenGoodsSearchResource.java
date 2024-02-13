package com.sellde.reward.rest.open;


import com.sellde.reward.service.biz.GoodsSearchBizService;
import com.sellde.reward.service.model.GoodsSearchModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("v1/open/goods-search")
public class OpenGoodsSearchResource {

    @Autowired
    private GoodsSearchBizService goodsSearchBizService;

    @PostMapping
    public Mono<GoodsSearchModel.Result> create(GoodsSearchModel.Request request) {
        return goodsSearchBizService.createAndUpdateCount(request);
    }

    @GetMapping("/top-10-keyword")
    public Mono<List<String>> topKeywords(@RequestParam(required = false) UUID customerId) {
        if (customerId != null) {
            return goodsSearchBizService.top10KeywordByCustomerId(customerId);
        }
        return goodsSearchBizService.top10Keyword();
    }

}
