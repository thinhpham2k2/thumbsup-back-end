package com.thumbsup.thumbsup.mapper;

import com.thumbsup.thumbsup.dto.payment.PaymentAccountDTO;
import com.thumbsup.thumbsup.entity.PaymentAccount;
import com.thumbsup.thumbsup.entity.Store;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface PaymentAccountMapper {

    PaymentAccountMapper INSTANCE = Mappers.getMapper(PaymentAccountMapper.class);

    @Mapping(target = "storeId", source = "store.id")
    @Mapping(target = "storeName", source = "store.id")
    PaymentAccountDTO toDTO(PaymentAccount entity);

    @Mapping(target = "store", source = "storeId", qualifiedByName = "mapStore")
    PaymentAccount dtoToEntity(PaymentAccountDTO dto);

    @Named("mapStore")
    default Store mapStore(Long id) {
        Store store = new Store();
        store.setId(id);
        return store;
    }
}
