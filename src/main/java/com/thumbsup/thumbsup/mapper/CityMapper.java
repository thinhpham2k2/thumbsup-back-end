package com.thumbsup.thumbsup.mapper;

import com.thumbsup.thumbsup.dto.CityDTO;
import com.thumbsup.thumbsup.entity.City;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CityMapper {

    CityMapper INSTANCE = Mappers.getMapper(CityMapper.class);

    CityDTO toDTO(City entity);

    @Mapping(target = "storeList", ignore = true)
    @Mapping(target = "customerList", ignore = true)
    City dtoToEntity(CityDTO dto);
}
