package com.thumbsup.thumbsup.mapper;

import com.thumbsup.thumbsup.dto.OrderDTO;
import com.thumbsup.thumbsup.dto.OrderDetailDTO;
import com.thumbsup.thumbsup.entity.Order;
import com.thumbsup.thumbsup.entity.OrderDetail;
import com.thumbsup.thumbsup.entity.OrderStore;
import com.thumbsup.thumbsup.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface OrderDetailMapper {

    OrderDetailMapper INSTANCE = Mappers.getMapper(OrderDetailMapper.class);

    @Mapping(target = "orderStoreId", source = "orderStore.id")
    @Mapping(target = "productId", source = "product.id")
    @Mapping(target = "productName", source = "product.productName")
    OrderDetailDTO toDTO(OrderDetail entity);

    @Mapping(target = "status", ignore = true)
    @Mapping(target = "orderStore", source = "orderStoreId", qualifiedByName = "mapOrderStore")
    @Mapping(target = "product", source = "productId", qualifiedByName = "mapProduct")
    OrderDetail dtoToEntity(OrderDetailDTO dto);

    @Named("mapOrderStore")
    default OrderStore mapOrderStore(Long id) {
        OrderStore orderStore = new OrderStore();
        orderStore.setId(id);
        return orderStore;
    }

    @Named("mapProduct")
    default Product mapProduct(Long id) {
        Product product = new Product();
        product.setId(id);
        return product;
    }
}
