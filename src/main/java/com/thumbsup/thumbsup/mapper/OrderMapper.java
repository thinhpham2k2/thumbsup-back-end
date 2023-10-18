package com.thumbsup.thumbsup.mapper;

import com.thumbsup.thumbsup.dto.order.OrderDTO;
import com.thumbsup.thumbsup.dto.order.OrderDetailDTO;
import com.thumbsup.thumbsup.dto.state.StateDetailDTO;
import com.thumbsup.thumbsup.entity.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    @Mapping(target = "customerId", source = "customer.id")
    @Mapping(target = "customerName", source = "customer.fullName")
    @Mapping(target = "stateCurrent", source = "stateDetailList", qualifiedByName = "mapStateCurrent")
    @Mapping(target = "orderDetailList", source = "orderDetailList", qualifiedByName = "mapOrderDetail")
    @Mapping(target = "stateDetailList", source = "stateDetailList", qualifiedByName = "mapStateDetail")
    OrderDTO toDTO(Order entity);

    @Mapping(target = "stateDetailList", ignore = true)
    @Mapping(target = "orderDetailList", ignore = true)
    @Mapping(target = "transactionOrderList", ignore = true)
    @Mapping(target = "customer", source = "customerId", qualifiedByName = "mapCustomer")
    Order dtoToEntity(OrderDTO dto);

    @Named("mapCustomer")
    default Customer mapCustomer(Long id) {
        Customer customer = new Customer();
        customer.setId(id);
        return customer;
    }

    @Named("mapOrderDetail")
    default List<OrderDetailDTO> mapOrderDetail(List<OrderDetail> orderDetailList) {
        return orderDetailList.stream().map(OrderDetailMapper.INSTANCE::toDTO).toList();
    }

    @Named("mapStateDetail")
    default List<StateDetailDTO> mapStateDetail(List<StateDetail> stateDetailList) {
        return stateDetailList.stream().map(StateDetailMapper.INSTANCE::toDTO).toList();
    }

    @Named("mapStateCurrent")
    default String mapStateCurrent(List<StateDetail> stateDetailList) {
        Optional<State> state = stateDetailList.stream().max(Comparator.comparingLong(StateDetail::getId)).map(StateDetail::getState);
        if (state.isPresent()){
            return state.get().getState();
        }
        return stateDetailList.stream().findFirst().map(s -> s.getState().getState()).orElse("");
    }
}
