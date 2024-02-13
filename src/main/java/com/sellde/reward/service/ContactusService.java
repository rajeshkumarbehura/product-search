package com.sellde.reward.service;

import com.sellde.reward.repository.ContactusRepository;
import com.sellde.reward.service.mapper.ContactusMapper;
import com.sellde.reward.service.model.ContactusModel;
import com.sellde.reward.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class ContactusService {

    @Autowired
    private ContactusRepository repository;

    @Autowired
    private ContactusMapper contactusMapper;

    public Mono<ContactusModel> create(ContactusModel model) {
        var phoneNo = Utils.cleanMobileNo(model.getPhoneNo());
        model.setPhoneNo(phoneNo);
        var today = LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        return repository.save(contactusMapper.toEntity(model))
                .map(contactusMapper::toModel);
    }

}
