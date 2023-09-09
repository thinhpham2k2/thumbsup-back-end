package com.thumbsup.thumbsup.service;

import com.thumbsup.thumbsup.dto.ProductDTO;
import com.thumbsup.thumbsup.entity.Customer;
import com.thumbsup.thumbsup.entity.Product;
import com.thumbsup.thumbsup.entity.Store;
import com.thumbsup.thumbsup.jwt.JwtTokenProvider;
import com.thumbsup.thumbsup.mapper.ProductMapper;
import com.thumbsup.thumbsup.repository.CustomerRepository;
import com.thumbsup.thumbsup.repository.ProductRepository;
import com.thumbsup.thumbsup.repository.StoreRepository;
import com.thumbsup.thumbsup.repository.WishlistProductRepository;
import com.thumbsup.thumbsup.service.interfaces.IPagingService;
import com.thumbsup.thumbsup.service.interfaces.IProductService;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductService implements IProductService {

    private final IPagingService pagingService;

    private final ProductRepository productRepository;

    private final StoreRepository storeRepository;

    private final CustomerRepository customerRepository;

    private final WishlistProductRepository wishlistProductRepository;

    @Override
    public Page<ProductDTO> getProductList(boolean status, List<Long> storeIds, List<Long> cateIds, List<Long> brandIds, List<Long> countryIds, String search, String sort, int page, int limit, String token) {
        if (page < 0) throw new InvalidParameterException("Page number must not be less than zero!");
        if (limit < 1) throw new InvalidParameterException("Page size must not be less than one!");

        List<Sort.Order> order = new ArrayList<>();

        String userName;
        if (CustomUserDetailsService.role.equals("Store")) {
            try {
                JwtTokenProvider jwtTokenProvider = new JwtTokenProvider();
                userName = jwtTokenProvider.getUserNameFromJWT(token);
            } catch (ExpiredJwtException e) {
                throw new InvalidParameterException("Expired JWT token");
            }
            storeIds.clear();
            Optional<Store> store = storeRepository.findStoreByUserNameAndStatus(userName, true);
            store.ifPresent(value -> storeIds.add(value.getId()));
        }

        Set<String> sourceFieldList = pagingService.getAllFields(Product.class);
        String[] subSort = sort.split(",");
        if (pagingService.checkPropertPresent(sourceFieldList, subSort[0])) {
            order.add(new Sort.Order(pagingService.getSortDirection(subSort[1]), transferProperty(subSort[0])));
        } else {
            throw new InvalidParameterException(subSort[0] + " is not a propertied of Product!");
        }

        Pageable pageable = PageRequest.of(page, limit).withSort(Sort.by(order));
        Page<Product> pageResult = productRepository.getProductList(true, storeIds, cateIds, brandIds, countryIds, search, pageable);

        if (CustomUserDetailsService.role.equals("Customer")) {
            try {
                JwtTokenProvider jwtTokenProvider = new JwtTokenProvider();
                userName = jwtTokenProvider.getUserNameFromJWT(token);
            } catch (ExpiredJwtException e) {
                throw new InvalidParameterException("Expired JWT token");
            }
            Optional<Customer> customer = customerRepository.findCustomerByUserNameAndStatus(userName, true);
            List<ProductDTO> productList = pageResult.stream().map(ProductMapper.INSTANCE::toDTO)
                    .peek(p -> p.setFavor(wishlistProductRepository
                            .existsByStatusAndCustomerIdAndProductId(true, customer.isPresent() ? customer.get().getId() : 1L, p.getId()))).toList();
            return new PageImpl<>(productList, pageResult.getPageable(), pageResult.getTotalElements());
        }

        return new PageImpl<>(pageResult.getContent().stream()
                .map(ProductMapper.INSTANCE::toDTO)
                .collect(Collectors.toList()), pageResult.getPageable(), pageResult.getTotalElements());
    }


    private static String transferProperty(String property) {
        return switch (property) {
            case "store" -> "store.storeName";
            case "category" -> "category.category";
            case "brand" -> "brand.brand";
            case "country" -> "country.country";
            default -> property;
        };
    }
}
