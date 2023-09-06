package com.thumbsup.thumbsup.mapper;

import com.thumbsup.thumbsup.dto.WishlistStoreDTO;
import com.thumbsup.thumbsup.entity.Customer;
import com.thumbsup.thumbsup.entity.Store;
import com.thumbsup.thumbsup.entity.WishlistStore;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface WishlistStoreMapper {

    WishlistStoreMapper INSTANCE = Mappers.getMapper(WishlistStoreMapper.class);

    @Mapping(target = "customerId", source = "customer.id")
    @Mapping(target = "customerName", source = "customer.fullName")
    @Mapping(target = "storeId", source = "store.id")
    @Mapping(target = "storeName", source = "store.storeName")
    WishlistStoreDTO toDTO(WishlistStore entity);

    @Mapping(target = "customer", source = "customerId", qualifiedByName = "mapCustomer")
    @Mapping(target = "store", source = "storeId", qualifiedByName = "mapStore")
    WishlistStore dtoToEntity(WishlistStoreDTO dto);

    @Named("mapCustomer")
    default Customer mapCustomer(Long id) {
        Customer customer = new Customer();
        customer.setId(id);
        return customer;
    }

    @Named("mapStore")
    default Store mapStore(Long id) {
        Store store = new Store();
        store.setId(id);
        return store;
    }
}
