package com.thumbsup.thumbsup.mapper;

import com.thumbsup.thumbsup.dto.state.StateDetailDTO;
import com.thumbsup.thumbsup.entity.Order;
import com.thumbsup.thumbsup.entity.State;
import com.thumbsup.thumbsup.entity.StateDetail;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface StateDetailMapper {

    StateDetailMapper INSTANCE = Mappers.getMapper(StateDetailMapper.class);

    @Mapping(target = "stateId", source = "state.id")
    @Mapping(target = "stateName", source = "state.state")
    @Mapping(target = "orderId", source = "order.id")
    StateDetailDTO toDTO(StateDetail entity);

    @Mapping(target = "state", source = "stateId", qualifiedByName = "mapState")
    @Mapping(target = "order", source = "orderId", qualifiedByName = "mapOrder")
    StateDetail dtoToEntity(StateDetailDTO dto);

    @Named("mapState")
    default State mapState(Long id) {
        State state = new State();
        state.setId(id);
        return state;
    }

    @Named("mapOrder")
    default Order mapOrder(Long id) {
        Order order = new Order();
        order.setId(id);
        return order;
    }
}
