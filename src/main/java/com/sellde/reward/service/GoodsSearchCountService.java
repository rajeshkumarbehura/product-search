package com.sellde.reward.service;

import com.sellde.reward.domain.GoodsSearchCount;
import com.sellde.reward.enums.StatusType;
import com.sellde.reward.repository.GoodsSearchCountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class GoodsSearchCountService {

    @Autowired
    private GoodsSearchCountRepository goodsSearchCountRepository;

    public Mono<GoodsSearchCount> create(String keyword) {

        var newDomain = new GoodsSearchCount();
        newDomain.setKeyword(keyword);
        newDomain.setUsedCount(1L);
        newDomain.setStatus(StatusType.ENABLE);

        return goodsSearchCountRepository.findOneByKeywordAndStatus(keyword, StatusType.ENABLE)
                .map(existing -> {
                    existing.setUsedCount(existing.getUsedCount() + 1);
                    return existing;
                })
                .defaultIfEmpty(newDomain)
                .flatMap(goodsSearchCountRepository::save);
    }

    public Flux<GoodsSearchCount> findTop10Keyword() {
        var sort = Sort.by(Sort.Direction.DESC, "usedCount");
        return goodsSearchCountRepository.findAllByStatus(StatusType.ENABLE, PageRequest.of(0, 10, sort));
    }
}
