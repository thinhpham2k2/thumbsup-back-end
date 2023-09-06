package com.thumbsup.thumbsup.mapper;

import com.thumbsup.thumbsup.dto.ImageDTO;
import com.thumbsup.thumbsup.dto.OrderDTO;
import com.thumbsup.thumbsup.dto.OrderStoreDTO;
import com.thumbsup.thumbsup.entity.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    @Mapping(target = "stateOrderId", source = "stateOrder.id")
    @Mapping(target = "stateName", source = "stateOrder.state")
    @Mapping(target = "customerId", source = "customer.id")
    @Mapping(target = "customerName", source = "customer.fullName")
    @Mapping(target = "orderStoreList", source = "orderStoreList", qualifiedByName = "mapOrderStore")
    OrderDTO toDTO(Order entity);

    @Mapping(target = "orderStoreList", ignore = true)
    @Mapping(target = "customer", source = "customerId", qualifiedByName = "mapCustomer")
    @Mapping(target = "stateOrder", source = "stateOrderId", qualifiedByName = "mapStateOrder")
    Order dtoToEntity(OrderDTO dto);

    @Named("mapStateOrder")
    default State mapStateOrder(Long id) {
        State state = new State();
        state.setId(id);
        return state;
    }

    @Named("mapCustomer")
    default Customer mapCustomer(Long id) {
        Customer customer = new Customer();
        customer.setId(id);
        return customer;
    }

    @Named("mapOrderStore")
    default List<OrderStoreDTO> mapOrderStore(List<OrderStore> orderStoreList) {
        return orderStoreList.stream().map(OrderStoreMapper.INSTANCE::toDTO).toList();
    }
}
