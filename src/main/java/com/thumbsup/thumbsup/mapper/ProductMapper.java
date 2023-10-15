package com.thumbsup.thumbsup.mapper;

import com.thumbsup.thumbsup.dto.image.ImageDTO;
import com.thumbsup.thumbsup.dto.product.CreateProductDTO;
import com.thumbsup.thumbsup.dto.product.ProductDTO;
import com.thumbsup.thumbsup.dto.product.ProductExtraDTO;
import com.thumbsup.thumbsup.dto.product.UpdateProductDTO;
import com.thumbsup.thumbsup.entity.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.Comparator;
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
    @Mapping(target = "imageCover", source = "imageList", qualifiedByName = "mapImage")
    ProductDTO toDTO(Product entity);

    @Mapping(target = "favor", ignore = true)
    @Mapping(target = "numOfSold", ignore = true)
    @Mapping(target = "storeId", source = "store.id")
    @Mapping(target = "storeLogo", source = "store.logo")
    @Mapping(target = "storeName", source = "store.storeName")
    @Mapping(target = "storeAddress", source = "store.address")
    @Mapping(target = "categoryId", source = "category.id")
    @Mapping(target = "categoryName", source = "category.category")
    @Mapping(target = "brandId", source = "brand.id")
    @Mapping(target = "brandName", source = "brand.brand")
    @Mapping(target = "countryId", source = "country.id")
    @Mapping(target = "countryName", source = "country.country")
    @Mapping(target = "imageList", source = "imageList", qualifiedByName = "mapImageList")
    ProductExtraDTO toExtraDTO(Product entity);

    @Mapping(target = "id", expression = "java(null)")
    @Mapping(target = "rating", expression = "java(java.math.BigDecimal.ZERO)")
    @Mapping(target = "numOfRating", expression = "java(0)")
    @Mapping(target = "status", expression = "java(true)")
    @Mapping(target = "wishlistProductList", ignore = true)
    @Mapping(target = "reviewList", ignore = true)
    @Mapping(target = "orderDetailList", ignore = true)
    @Mapping(target = "imageList", ignore = true)
    @Mapping(target = "store", source = "storeId", qualifiedByName = "mapStore")
    @Mapping(target = "category", source = "categoryId", qualifiedByName = "mapCategory")
    @Mapping(target = "brand", source = "brandId", qualifiedByName = "mapBrand")
    @Mapping(target = "country", source = "countryId", qualifiedByName = "mapCountry")
    Product createToEntity(CreateProductDTO createDTO);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "rating", ignore = true)
    @Mapping(target = "numOfRating", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "wishlistProductList", ignore = true)
    @Mapping(target = "reviewList", ignore = true)
    @Mapping(target = "orderDetailList", ignore = true)
    @Mapping(target = "imageList", ignore = true)
    @Mapping(target = "store", source = "storeId", qualifiedByName = "mapStore")
    @Mapping(target = "category", source = "categoryId", qualifiedByName = "mapCategory")
    @Mapping(target = "brand", source = "brandId", qualifiedByName = "mapBrand")
    @Mapping(target = "country", source = "countryId", qualifiedByName = "mapCountry")
    Product updateToEntity(UpdateProductDTO updateDTO, @MappingTarget Product entity);

    @Named("mapImage")
    default String mapImage(List<Image> imageList) {
        return imageList.stream().filter(i -> i.getIsCover().equals(true)).findFirst()
                .map(Image::getUrl).orElse("");
    }

    @Named("mapImageList")
    default List<String> mapImageList(List<Image> imageList) {
        return imageList.stream().map(ImageMapper.INSTANCE::toDTO)
                .sorted(Comparator.comparing(ImageDTO::getIsCover).reversed())
                .map(ImageDTO::getUrl).toList();
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
