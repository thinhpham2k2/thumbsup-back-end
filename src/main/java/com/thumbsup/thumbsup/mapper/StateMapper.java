package com.thumbsup.thumbsup.mapper;

import com.thumbsup.thumbsup.dto.StateDTO;
import com.thumbsup.thumbsup.entity.State;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface StateMapper {

    StateMapper INSTANCE = Mappers.getMapper(StateMapper.class);

    StateDTO toDTO(State entity);

    @Mapping(target = "orderList", ignore = true)
    @Mapping(target = "stateDetailList", ignore = true)
    State dtoToEntity(StateDTO dto);
}
