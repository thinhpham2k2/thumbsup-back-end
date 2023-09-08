package com.thumbsup.thumbsup.mapper;

import com.thumbsup.thumbsup.dto.OrderDTO;
import com.thumbsup.thumbsup.dto.OrderDetailDTO;
import com.thumbsup.thumbsup.dto.StateDetailDTO;
import com.thumbsup.thumbsup.entity.Customer;
import com.thumbsup.thumbsup.entity.Order;
import com.thumbsup.thumbsup.entity.OrderDetail;
import com.thumbsup.thumbsup.entity.StateDetail;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    @Mapping(target = "customerId", source = "customer.id")
    @Mapping(target = "customerName", source = "customer.fullName")
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
}
