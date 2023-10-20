package com.thumbsup.thumbsup.mapper;

import com.thumbsup.thumbsup.dto.ads.AdvertisementDTO;
import com.thumbsup.thumbsup.dto.ads.CreateAdvertisementDTO;
import com.thumbsup.thumbsup.entity.Advertisement;
import com.thumbsup.thumbsup.entity.Store;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;

@Mapper(componentModel = "spring")
public interface AdvertisementMapper {

    AdvertisementMapper INSTANCE = Mappers.getMapper(AdvertisementMapper.class);

    @Mapping(target = "storeId", source = "store.id")
    @Mapping(target = "storeName", source = "store.storeName")
    @Mapping(target = "storeLogo", source = "store.logo")
    @Mapping(target = "storeImageCover", source = "store.coverPhoto")
    AdvertisementDTO toDTO(Advertisement entity);

    @Mapping(target = "id", expression = "java(null)")
    @Mapping(target = "status", expression = "java(true)")
    @Mapping(target = "clickCount", expression = "java(0)")
    @Mapping(target = "dateExpired", source = "duration", qualifiedByName = "mapDateExpired")
    @Mapping(target = "dateCreated", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "store", source = "storeId", qualifiedByName = "mapStore")
    Advertisement createToEntity(CreateAdvertisementDTO create);

    @Named("mapStore")
    default Store mapStore(Long id) {
        Store store = new Store();
        store.setId(id);
        return store;
    }

    @Named("mapDateExpired")
    default LocalDateTime mapDateExpired(Integer duration) {
        return LocalDateTime.now().plusWeeks(duration);
    }
}
