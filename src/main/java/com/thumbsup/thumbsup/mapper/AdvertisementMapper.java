package com.thumbsup.thumbsup.mapper;

import com.thumbsup.thumbsup.dto.AdvertisementDTO;
import com.thumbsup.thumbsup.entity.Advertisement;
import com.thumbsup.thumbsup.entity.Store;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface AdvertisementMapper {

    AdvertisementMapper INSTANCE = Mappers.getMapper(AdvertisementMapper.class);

    @Mapping(target = "storeId", source = "store.id")
    @Mapping(target = "storeName", source = "store.storeName")
    @Mapping(target = "storeLogo", source = "store.logo")
    @Mapping(target = "storeImageCover", source = "store.coverPhoto")
    AdvertisementDTO toDTO(Advertisement entity);

    @Mapping(target = "store", source = "storeId", qualifiedByName = "mapStore")
    Advertisement dtoToEntity(AdvertisementDTO dto);

    @Named("mapStore")
    default Store mapStore(Long id) {
        Store store = new Store();
        store.setId(id);
        return store;
    }
}
