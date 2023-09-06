package com.thumbsup.thumbsup.mapper;

import com.thumbsup.thumbsup.dto.CountryDTO;
import com.thumbsup.thumbsup.entity.Country;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CountryMapper {

    CountryMapper INSTANCE = Mappers.getMapper(CountryMapper.class);

    CountryDTO toDTO(Country entity);

    @Mapping(target = "productList", ignore = true)
    Country dtoToEntity(CountryDTO dto);
}
