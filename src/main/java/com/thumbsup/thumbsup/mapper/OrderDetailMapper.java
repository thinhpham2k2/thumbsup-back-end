package com.thumbsup.thumbsup.mapper;

import com.thumbsup.thumbsup.dto.order.OrderDetailDTO;
import com.thumbsup.thumbsup.entity.Order;
import com.thumbsup.thumbsup.entity.OrderDetail;
import com.thumbsup.thumbsup.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface OrderDetailMapper {

    OrderDetailMapper INSTANCE = Mappers.getMapper(OrderDetailMapper.class);

    @Mapping(target = "orderId", source = "order.id")
    @Mapping(target = "productId", source = "product.id")
    @Mapping(target = "productName", source = "product.productName")
    OrderDetailDTO toDTO(OrderDetail entity);

    @Mapping(target = "order", source = "orderId", qualifiedByName = "mapOrder")
    @Mapping(target = "product", source = "productId", qualifiedByName = "mapProduct")
    OrderDetail dtoToEntity(OrderDetailDTO dto);

    @Named("mapOrder")
    default Order mapOrder(Long id) {
        Order order = new Order();
        order.setId(id);
        return order;
    }

    @Named("mapProduct")
    default Product mapProduct(Long id) {
        Product product = new Product();
        product.setId(id);
        return product;
    }
}
