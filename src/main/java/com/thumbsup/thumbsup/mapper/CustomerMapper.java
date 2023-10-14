package com.thumbsup.thumbsup.mapper;

import com.thumbsup.thumbsup.dto.customer.CreateCustomerDTO;
import com.thumbsup.thumbsup.dto.customer.CustomerDTO;
import com.thumbsup.thumbsup.dto.customer.UpdateCustomerDTO;
import com.thumbsup.thumbsup.entity.City;
import com.thumbsup.thumbsup.entity.Customer;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);

    @Mapping(target = "cityId", source = "city.id")
    @Mapping(target = "cityName", source = "city.cityName")
    CustomerDTO toDTO(Customer entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "wishlistProductList", ignore = true)
    @Mapping(target = "wishlistStoreList", ignore = true)
    @Mapping(target = "reviewList", ignore = true)
    @Mapping(target = "orderList", ignore = true)
    @Mapping(target = "state", expression = "java(true)")
    @Mapping(target = "status", expression = "java(true)")
    @Mapping(target = "city", source = "cityId", qualifiedByName = "mapCity")
    Customer createToEntity(CreateCustomerDTO createDTO);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "userName", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "email", ignore = true)
    @Mapping(target = "state", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "avatar", ignore = true)
    @Mapping(target = "fullName", source = "updateDTO.fullName")
    @Mapping(target = "phone", source = "updateDTO.phone")
    @Mapping(target = "dob", source = "updateDTO.dob")
    @Mapping(target = "address", source = "updateDTO.address")
    @Mapping(target = "city", source = "updateDTO.cityId", qualifiedByName = "mapCity")
    @Mapping(target = "wishlistProductList", ignore = true)
    @Mapping(target = "wishlistStoreList", ignore = true)
    @Mapping(target = "reviewList", ignore = true)
    @Mapping(target = "orderList", ignore = true)
    Customer updateToEntity(UpdateCustomerDTO updateDTO, @MappingTarget Customer entity);

    @Named("mapCity")
    default City mapCity(Long id) {
        City city = new City();
        city.setId(id);
        return city;
    }

    @AfterMapping
    default void  mapAvatar(UpdateCustomerDTO updateDTO, @MappingTarget Customer entity) {
        if(!updateDTO.getAvatar().isBlank()){
            entity.setAvatar(updateDTO.getAvatar());
        }
    }
}
