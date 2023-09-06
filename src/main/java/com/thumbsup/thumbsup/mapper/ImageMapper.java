package com.thumbsup.thumbsup.mapper;

import com.thumbsup.thumbsup.dto.CustomerDTO;
import com.thumbsup.thumbsup.dto.ImageDTO;
import com.thumbsup.thumbsup.entity.City;
import com.thumbsup.thumbsup.entity.Customer;
import com.thumbsup.thumbsup.entity.Image;
import com.thumbsup.thumbsup.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ImageMapper {

    ImageMapper INSTANCE = Mappers.getMapper(ImageMapper.class);

    @Mapping(target = "productId", source = "product.id")
    @Mapping(target = "productName", source = "product.productName")
    ImageDTO toDTO(Image entity);

    @Mapping(target = "status", ignore = true)
    @Mapping(target = "product", source = "productId", qualifiedByName = "mapProduct")
    Image dtoToEntity(ImageDTO dto);

    @Named("mapProduct")
    default Product mapProduct(Long id) {
        Product product = new Product();
        product.setId(id);
        return product;
    }
}
