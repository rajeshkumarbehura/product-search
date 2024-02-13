package com.sellde.reward.rest.open;

import com.sellde.reward.service.LuckyDrawContestService;
import com.sellde.reward.service.biz.StorePromoBizService;
import com.sellde.reward.service.model.LuckyDrawContestModel;
import com.sellde.reward.service.model.StorePromoModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("v1/open/lucky-draw-contest")
public class LuckyDrawContestResource {

    @Autowired
    private LuckyDrawContestService luckyDrawContestService;

    @PostMapping()
    @ResponseStatus(HttpStatus.OK)
    public Mono<LuckyDrawContestModel> create(@RequestBody LuckyDrawContestModel model) {
        return luckyDrawContestService.create(model);
    }
}
