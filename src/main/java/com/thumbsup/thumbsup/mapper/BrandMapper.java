package com.thumbsup.thumbsup.mapper;

import com.thumbsup.thumbsup.dto.brand.BrandDTO;
import com.thumbsup.thumbsup.entity.Brand;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface BrandMapper {

    BrandMapper INSTANCE = Mappers.getMapper(BrandMapper.class);

    BrandDTO toDTO(Brand entity);

    @Mapping(target = "productList", ignore = true)
    Brand dtoToEntity(BrandDTO dto);
}
