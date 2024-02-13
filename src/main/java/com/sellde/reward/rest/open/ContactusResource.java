package com.sellde.reward.rest.open;

import com.sellde.reward.service.ContactusService;
import com.sellde.reward.service.model.ContactusModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("v1/open/contactus")
public class ContactusResource {

    @Autowired
    private ContactusService contactusService;

    @PostMapping()
    @ResponseStatus(HttpStatus.OK)
    public Mono<ContactusModel> create(@RequestBody ContactusModel model) {
        return contactusService.create(model);
    }
}
