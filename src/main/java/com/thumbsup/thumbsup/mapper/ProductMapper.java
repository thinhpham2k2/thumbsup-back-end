package com.thumbsup.thumbsup.mapper;

import com.thumbsup.thumbsup.dto.ImageDTO;
import com.thumbsup.thumbsup.dto.ProductDTO;
import com.thumbsup.thumbsup.entity.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    @Mapping(target = "favor", ignore = true)
    @Mapping(target = "storeId", source = "store.id")
    @Mapping(target = "storeName", source = "store.storeName")
    @Mapping(target = "categoryId", source = "category.id")
    @Mapping(target = "categoryName", source = "category.category")
    @Mapping(target = "brandId", source = "brand.id")
    @Mapping(target = "brandName", source = "brand.brand")
    @Mapping(target = "countryId", source = "country.id")
    @Mapping(target = "countryName", source = "country.country")
    @Mapping(target = "imageList", source = "imageList", qualifiedByName = "mapImage")
    ProductDTO toDTO(Product entity);

    @Mapping(target = "wishlistProductList", ignore = true)
    @Mapping(target = "reviewList", ignore = true)
    @Mapping(target = "orderDetailList", ignore = true)
    @Mapping(target = "imageList", ignore = true)
    @Mapping(target = "store", source = "storeId", qualifiedByName = "mapStore")
    @Mapping(target = "category", source = "categoryId", qualifiedByName = "mapCategory")
    @Mapping(target = "brand", source = "brandId", qualifiedByName = "mapBrand")
    @Mapping(target = "country", source = "countryId", qualifiedByName = "mapCountry")
    Product dtoToEntity(ProductDTO dto);

    @Named("mapImage")
    default List<ImageDTO> mapImage(List<Image> imageList) {
        return imageList.stream().map(ImageMapper.INSTANCE::toDTO).toList();
    }

    @Named("mapStore")
    default Store mapStore(Long id) {
        Store store = new Store();
        store.setId(id);
        return store;
    }

    @Named("mapCategory")
    default Category mapCategory(Long id) {
        Category category = new Category();
        category.setId(id);
        return category;
    }

    @Named("mapBrand")
    default Brand mapBrand(Long id) {
        Brand brand = new Brand();
        brand.setId(id);
        return brand;
    }

    @Named("mapCountry")
    default Country mapCountry(Long id) {
        Country country = new Country();
        country.setId(id);
        return country;
    }
}
