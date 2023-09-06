package com.thumbsup.thumbsup.mapper;

import com.thumbsup.thumbsup.dto.BrandDTO;
import com.thumbsup.thumbsup.entity.Brand;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface BrandMapper {

    BrandMapper INSTANCE = Mappers.getMapper(BrandMapper.class);

    BrandDTO toDTO(Brand entity);

    @Mapping(target = "status", ignore = true)
    @Mapping(target = "productList", ignore = true)
    Brand dtoToEntity(BrandDTO dto);
}
