package com.sellde.reward.service.mapper;

import com.sellde.reward.domain.UserProfileCopy;
import com.sellde.reward.enums.StatusType;
import com.sellde.reward.service.model.UserModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring", uses = {})
public interface UserProfileCopyMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "userId", source = "id")
    @Mapping(target = "userName", source = "name")
    @Mapping(target = "phoneNo", source = "mobileNo")
    @Mapping(target = "userType", source = "type")
    UserProfileCopy toEntity(UserModel.Request model);


    UserModel.ProfileModel toModel(UserProfileCopy entity);

}

