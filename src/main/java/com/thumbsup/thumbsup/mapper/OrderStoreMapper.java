package com.thumbsup.thumbsup.mapper;

import com.thumbsup.thumbsup.dto.OrderDTO;
import com.thumbsup.thumbsup.dto.OrderDetailDTO;
import com.thumbsup.thumbsup.dto.OrderStoreDTO;
import com.thumbsup.thumbsup.dto.StateDetailDTO;
import com.thumbsup.thumbsup.entity.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderStoreMapper {

    OrderStoreMapper INSTANCE = Mappers.getMapper(OrderStoreMapper.class);

    @Mapping(target = "orderId", source = "order.id")
    @Mapping(target = "orderDetailList", source = "orderDetailList", qualifiedByName = "mapOrderDetail")
    @Mapping(target = "stateDetailList", source = "stateDetailList", qualifiedByName = "mapStateDetail")
    OrderStoreDTO toDTO(OrderStore entity);

    @Mapping(target = "status", ignore = true)
    @Mapping(target = "stateDetailList", ignore = true)
    @Mapping(target = "orderDetailList", ignore = true)
    @Mapping(target = "order", source = "orderId", qualifiedByName = "mapOrder")
    OrderStore dtoToEntity(OrderStoreDTO dto);

    @Named("mapOrder")
    default Order mapOrder(Long id) {
        Order order = new Order();
        order.setId(id);
        return order;
    }

    @Named("mapOrderDetail")
    default List<OrderDetailDTO> mapOrderDetail(List<OrderDetail> orderDetailList) {
        return orderDetailList.stream().map(OrderDetailMapper.INSTANCE::toDTO).toList();
    }

    @Named("mapStateDetail")
    default List<StateDetailDTO> mapStateDetail(List<StateDetail> stateDetailList) {
        return stateDetailList.stream().map(StateDetailMapper.INSTANCE::toDTO).toList();
    }
}
