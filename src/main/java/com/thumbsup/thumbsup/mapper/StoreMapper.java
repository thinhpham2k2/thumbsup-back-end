package com.thumbsup.thumbsup.mapper;

import com.thumbsup.thumbsup.dto.StoreDTO;
import com.thumbsup.thumbsup.entity.City;
import com.thumbsup.thumbsup.entity.Store;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface StoreMapper {

    StoreMapper INSTANCE = Mappers.getMapper(StoreMapper.class);

    @Mapping(target = "cityId", source = "city.id")
    @Mapping(target = "cityName", source = "city.cityName")
    StoreDTO toDTO(Store entity);

    @Mapping(target = "password", ignore = true)
    @Mapping(target = "adsList", ignore = true)
    @Mapping(target = "productList", ignore = true)
    @Mapping(target = "wishlistStoreList", ignore = true)
    @Mapping(target = "city", source = "cityId", qualifiedByName = "mapCity")
    Store dtoToEntity(StoreDTO dto);

    @Named("mapCity")
    default City mapCity(Long id) {
        City city = new City();
        city.setId(id);
        return city;
    }
}