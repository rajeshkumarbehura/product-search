package com.sellde.reward.service.mapper;

import com.sellde.reward.domain.Contactus;
import com.sellde.reward.service.model.ContactusModel;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring", uses = {})
public interface ContactusMapper extends EntityMapper<ContactusModel, Contactus> {
}

