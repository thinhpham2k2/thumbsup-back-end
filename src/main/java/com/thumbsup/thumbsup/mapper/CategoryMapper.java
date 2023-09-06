package com.thumbsup.thumbsup.mapper;

import com.thumbsup.thumbsup.dto.CategoryDTO;
import com.thumbsup.thumbsup.entity.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);

    CategoryDTO toDTO(Category entity);

    @Mapping(target = "productList", ignore = true)
    Category dtoToEntity(CategoryDTO dto);
}
