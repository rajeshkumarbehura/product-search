package com.sellde.reward.service.mapper;

import com.sellde.reward.domain.SignupQnA;
import com.sellde.reward.service.model.SignupQnAModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring", uses = {})
public interface SignupQnAMapper extends EntityMapper<SignupQnAModel.RequestResponse, SignupQnA> {

    @Named("toUpdateEntity")
    @Mapping(target = "id", ignore = true)
    SignupQnA toUpdateEntity(@MappingTarget SignupQnA entity, SignupQnAModel.RequestResponse model);

    SignupQnAModel.GetQuery toOnlyQuery(SignupQnA entity);
}

