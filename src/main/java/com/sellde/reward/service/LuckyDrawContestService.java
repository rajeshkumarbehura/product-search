package com.sellde.reward.service;

import com.sellde.reward.repository.LuckyDrawContestRepository;
import com.sellde.reward.service.mapper.LuckyDrawContestMapper;
import com.sellde.reward.service.model.LuckyDrawContestModel;
import com.sellde.reward.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class LuckyDrawContestService {

    @Autowired
    private LuckyDrawContestRepository repository;

    @Autowired
    private LuckyDrawContestMapper luckyDrawContestMapper;

    public Mono<LuckyDrawContestModel> create(LuckyDrawContestModel model) {
        var phoneNo = Utils.cleanMobileNo(model.getPhoneNo());
        model.setPhoneNo(phoneNo);
        var today = LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        return repository.findBYPhoneAndDay(phoneNo, today)
                .switchIfEmpty(repository.save(luckyDrawContestMapper.toEntity(model)))
                .map(luckyDrawContestMapper::toModel);
    }

}
