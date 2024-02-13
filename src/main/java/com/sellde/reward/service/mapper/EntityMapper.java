package com.sellde.reward.service.mapper;

import org.mapstruct.*;

import java.util.List;


public interface EntityMapper<M, E> {
    E toEntity(M model);

    M toModel(E entity);

    List<E> toEntity(List<M> dtoList);

    List<M> toModel(List<E> entityList);

    @Named("partialUpdate")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void partialUpdate(@MappingTarget E entity, M model);

    @Named("toUpdateEntity")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "lastModifiedDate", ignore = true)
    E toUpdateEntity(@MappingTarget E entity, M model);
}
