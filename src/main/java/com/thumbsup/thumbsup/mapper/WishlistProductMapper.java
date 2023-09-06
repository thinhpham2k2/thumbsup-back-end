package com.thumbsup.thumbsup.mapper;

import com.thumbsup.thumbsup.dto.WishlistProductDTO;
import com.thumbsup.thumbsup.entity.Customer;
import com.thumbsup.thumbsup.entity.Product;
import com.thumbsup.thumbsup.entity.WishlistProduct;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface WishlistProductMapper {

    WishlistProductMapper INSTANCE = Mappers.getMapper(WishlistProductMapper.class);

    @Mapping(target = "customerId", source = "customer.id")
    @Mapping(target = "customerName", source = "customer.fullName")
    @Mapping(target = "productId", source = "product.id")
    @Mapping(target = "productName", source = "product.productName")
    WishlistProductDTO toDTO(WishlistProduct entity);

    @Mapping(target = "customer", source = "customerId", qualifiedByName = "mapCustomer")
    @Mapping(target = "product", source = "productId", qualifiedByName = "mapProduct")
    WishlistProduct dtoToEntity(WishlistProductDTO dto);

    @Named("mapCustomer")
    default Customer mapCustomer(Long id) {
        Customer customer = new Customer();
        customer.setId(id);
        return customer;
    }

    @Named("mapProduct")
    default Product mapProduct(Long id) {
        Product product = new Product();
        product.setId(id);
        return product;
    }
}
