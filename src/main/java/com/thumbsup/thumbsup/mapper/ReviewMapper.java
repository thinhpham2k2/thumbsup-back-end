package com.thumbsup.thumbsup.mapper;

import com.thumbsup.thumbsup.dto.ReviewDTO;
import com.thumbsup.thumbsup.entity.Customer;
import com.thumbsup.thumbsup.entity.Product;
import com.thumbsup.thumbsup.entity.Review;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ReviewMapper {

    ReviewMapper INSTANCE = Mappers.getMapper(ReviewMapper.class);

    @Mapping(target = "customerId", source = "customer.id")
    @Mapping(target = "customerName", source = "customer.fullName")
    @Mapping(target = "productId", source = "product.id")
    @Mapping(target = "productName", source = "product.productName")
    ReviewDTO toDTO(Review entity);

    @Mapping(target = "customer", source = "customerId", qualifiedByName = "mapCustomer")
    @Mapping(target = "product", source = "productId", qualifiedByName = "mapProduct")
    Review dtoToEntity(ReviewDTO dto);

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
