package com.thumbsup.thumbsup.mapper;

import com.thumbsup.thumbsup.dto.request.RequestDTO;
import com.thumbsup.thumbsup.entity.Request;
import com.thumbsup.thumbsup.entity.Store;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface RequestMapper {

    RequestMapper INSTANCE = Mappers.getMapper(RequestMapper.class);

    @Mapping(target = "storeId", source = "store.id")
    @Mapping(target = "storeName", source = "store.storeName")
    @Mapping(target = "storeLogo", source = "store.logo")
    @Mapping(target = "storeImageCover", source = "store.coverPhoto")
    RequestDTO toDTO(Request entity);

    @Mapping(target = "store", source = "storeId", qualifiedByName = "mapStore")
    Request dtoToEntity(RequestDTO dto);

    @Named("mapStore")
    default Store mapStore(Long id) {
        Store store = new Store();
        store.setId(id);
        return store;
    }
}
