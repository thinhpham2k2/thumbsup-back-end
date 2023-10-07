package com.thumbsup.thumbsup.mapper;

import com.thumbsup.thumbsup.dto.admin.AdminDTO;
import com.thumbsup.thumbsup.entity.Admin;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface AdminMapper {

    AdminMapper INSTANCE = Mappers.getMapper(AdminMapper.class);

    AdminDTO toDTO(Admin entity);

    @Mapping(target = "password", ignore = true)
    Admin dtoToEntity(AdminDTO dto);
}
