package com.thumbsup.thumbsup.mapper;

import com.thumbsup.thumbsup.dto.order.CreateOrderDetailDTO;
import com.thumbsup.thumbsup.dto.order.OrderDetailDTO;
import com.thumbsup.thumbsup.entity.Image;
import com.thumbsup.thumbsup.entity.OrderDetail;
import com.thumbsup.thumbsup.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderDetailMapper {

    OrderDetailMapper INSTANCE = Mappers.getMapper(OrderDetailMapper.class);

    @Mapping(target = "orderId", source = "order.id")
    @Mapping(target = "productId", source = "product.id")
    @Mapping(target = "productName", source = "product.productName")
    @Mapping(target = "productImage", source = "product.imageList", qualifiedByName = "mapImage")
    OrderDetailDTO toDTO(OrderDetail entity);

    @Mapping(target = "id", expression = "java(null)")
    @Mapping(target = "status", expression = "java(true)")
    @Mapping(target = "order", ignore = true)
    @Mapping(target = "product", source = "productId", qualifiedByName = "mapProduct")
    OrderDetail createToEntity(CreateOrderDetailDTO create);

    @Named("mapProduct")
    default Product mapProduct(Long id) {
        Product product = new Product();
        product.setId(id);
        return product;
    }

    @Named("mapImage")
    default String mapImage(List<Image> imageList) {
        return imageList.stream().filter(i -> i.getIsCover().equals(true)).findFirst()
                .map(Image::getUrl).orElse("");
    }
}
