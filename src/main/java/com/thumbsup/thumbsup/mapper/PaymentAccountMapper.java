package com.thumbsup.thumbsup.mapper;

import com.thumbsup.thumbsup.dto.payment.CreatePaymentAccountDTO;
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
    @Mapping(target = "storeLogo", source = "store.logo")
    PaymentAccountDTO toDTO(PaymentAccount entity);

    @Mapping(target = "id", expression = "java(null)")
    @Mapping(target = "status", expression = "java(true)")
    @Mapping(target = "dateCreated", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "store", source = "storeId", qualifiedByName = "mapStore")
    PaymentAccount createToEntity(CreatePaymentAccountDTO create);

    @Named("mapStore")
    default Store mapStore(Long id) {
        Store store = new Store();
        store.setId(id);
        return store;
    }
}
