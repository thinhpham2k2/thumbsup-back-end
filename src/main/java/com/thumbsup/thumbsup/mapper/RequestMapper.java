package com.thumbsup.thumbsup.mapper;

import com.thumbsup.thumbsup.dto.request.CreateRequestDTO;
import com.thumbsup.thumbsup.dto.request.RequestDTO;
import com.thumbsup.thumbsup.dto.request.UpdateRequestDTO;
import com.thumbsup.thumbsup.entity.Request;
import com.thumbsup.thumbsup.entity.Store;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
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

    @Mapping(target = "id", expression = "java(null)")
    @Mapping(target = "amount", ignore = true)
    @Mapping(target = "method", ignore = true)
    @Mapping(target = "status", expression = "java(true)")
    @Mapping(target = "dateCreated", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "dateAccept", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "store", source = "storeId", qualifiedByName = "mapStore")
    Request createToEntity(CreateRequestDTO create);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "amount", ignore = true)
    @Mapping(target = "method", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "dateCreated", ignore = true)
    @Mapping(target = "dateAccept", ignore = true)
    @Mapping(target = "store", ignore = true)
    Request updateToEntity(UpdateRequestDTO update, @MappingTarget Request entity);

    @Named("mapStore")
    default Store mapStore(Long id) {
        Store store = new Store();
        store.setId(id);
        return store;
    }
}
