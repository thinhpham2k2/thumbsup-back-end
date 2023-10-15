package com.thumbsup.thumbsup.mapper;

import com.thumbsup.thumbsup.dto.store.CreateStoreDTO;
import com.thumbsup.thumbsup.dto.store.StoreDTO;
import com.thumbsup.thumbsup.dto.store.StoreExtraDTO;
import com.thumbsup.thumbsup.dto.store.UpdateStoreDTO;
import com.thumbsup.thumbsup.entity.City;
import com.thumbsup.thumbsup.entity.Store;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface StoreMapper {

    StoreMapper INSTANCE = Mappers.getMapper(StoreMapper.class);

    @Mapping(target = "cityId", source = "city.id")
    @Mapping(target = "cityName", source = "city.cityName")
    StoreDTO toDTO(Store entity);

    @Mapping(target = "favor", ignore = true)
    @Mapping(target = "rating", ignore = true)
    @Mapping(target = "cateList", ignore = true)
    @Mapping(target = "numOfRating", ignore = true)
    @Mapping(target = "numOfFollowing", ignore = true)
    @Mapping(target = "cityId", source = "city.id")
    @Mapping(target = "cityName", source = "city.cityName")
    StoreExtraDTO toExtraDTO(Store entity);

    @Mapping(target = "id", expression = "java(null)")
    @Mapping(target = "balance", expression = "java(java.math.BigDecimal.ZERO)")
    @Mapping(target = "dateCreated", expression = "java(java.time.LocalDate.now())")
    @Mapping(target = "adsList", ignore = true)
    @Mapping(target = "productList", ignore = true)
    @Mapping(target = "wishlistStoreList", ignore = true)
    @Mapping(target = "transactionOrderList", ignore = true)
    @Mapping(target = "paymentAccountList", ignore = true)
    @Mapping(target = "requestList", ignore = true)
    @Mapping(target = "state", expression = "java(true)")
    @Mapping(target = "status", expression = "java(true)")
    @Mapping(target = "city", source = "cityId", qualifiedByName = "mapCity")
    Store createToEntity(CreateStoreDTO createDTO);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "userName", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "balance", ignore = true)
    @Mapping(target = "dateCreated", ignore = true)
    @Mapping(target = "state", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "adsList", ignore = true)
    @Mapping(target = "productList", ignore = true)
    @Mapping(target = "wishlistStoreList", ignore = true)
    @Mapping(target = "transactionOrderList", ignore = true)
    @Mapping(target = "paymentAccountList", ignore = true)
    @Mapping(target = "requestList", ignore = true)
    @Mapping(target = "city", source = "updateDTO.cityId", qualifiedByName = "mapCity")
    Store updateToEntity(UpdateStoreDTO updateDTO, @MappingTarget Store entity);

    @Named("mapCity")
    default City mapCity(Long id) {
        City city = new City();
        city.setId(id);
        return city;
    }
}
