package com.thumbsup.thumbsup.mapper;

import com.thumbsup.thumbsup.dto.TransactionOrderDTO;
import com.thumbsup.thumbsup.entity.Order;
import com.thumbsup.thumbsup.entity.Store;
import com.thumbsup.thumbsup.entity.TransactionOrder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface TransactionOrderMapper {

    TransactionOrderMapper INSTANCE = Mappers.getMapper(TransactionOrderMapper.class);

    @Mapping(target = "orderId", source = "order.id")
    @Mapping(target = "storeId", source = "store.id")
    @Mapping(target = "storeName", source = "store.id")
    TransactionOrderDTO toDTO(TransactionOrder entity);

    @Mapping(target = "order", source = "orderId", qualifiedByName = "mapOrder")
    @Mapping(target = "store", source = "storeId", qualifiedByName = "mapStore")
    TransactionOrder dtoToEntity(TransactionOrderDTO dto);

    @Named("mapOrder")
    default Order mapOrder(Long id) {
        Order order = new Order();
        order.setId(id);
        return order;
    }

    @Named("mapStore")
    default Store mapStore(Long id) {
        Store store = new Store();
        store.setId(id);
        return store;
    }
}
