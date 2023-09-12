package com.thumbsup.thumbsup.service;

import com.thumbsup.thumbsup.common.Common;
import com.thumbsup.thumbsup.dto.ProductDTO;
import com.thumbsup.thumbsup.dto.ProductExtraDTO;
import com.thumbsup.thumbsup.entity.Customer;
import com.thumbsup.thumbsup.entity.Product;
import com.thumbsup.thumbsup.entity.Store;
import com.thumbsup.thumbsup.mapper.ProductMapper;
import com.thumbsup.thumbsup.repository.*;
import com.thumbsup.thumbsup.service.interfaces.IPagingService;
import com.thumbsup.thumbsup.service.interfaces.IProductService;
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

    private final StoreRepository storeRepository;

    private final ProductRepository productRepository;

    private final CustomerRepository customerRepository;

    private final WishlistProductRepository wishlistProductRepository;

    private final OrderDetailRepository detailRepository;

    @Override
    public Page<ProductDTO> getProductList(boolean status, List<Long> storeIds, List<Long> cateIds, List<Long> brandIds, List<Long> countryIds, String search, String sort, int page, int limit) {
        if (page < 0) throw new InvalidParameterException("Page number must not be less than zero!");
        if (limit < 1) throw new InvalidParameterException("Page size must not be less than one!");

        List<Sort.Order> order = new ArrayList<>();

        String userName = Common.userName;

        Set<String> sourceFieldList = pagingService.getAllFields(Product.class);
        String[] subSort = sort.split(",");
        if (pagingService.checkPropertPresent(sourceFieldList, subSort[0])) {
            order.add(new Sort.Order(pagingService.getSortDirection(subSort[1]), transferProperty(subSort[0])));
        } else {
            throw new InvalidParameterException(subSort[0] + " is not a propertied of Product!");
        }

        Pageable pageable = PageRequest.of(page, limit).withSort(Sort.by(order));
        Page<Product> pageResult = productRepository.getProductList(status, storeIds, cateIds, brandIds, countryIds, search, pageable);

        if (Common.role.equals("Customer")) {
            Optional<Customer> customer = customerRepository.findCustomerByUserNameAndStatus(userName, true);
            if (customer.isPresent()) {
                List<ProductDTO> productList = pageResult.stream().map(ProductMapper.INSTANCE::toDTO)
                        .peek(p -> p.setFavor(wishlistProductRepository
                                .existsByStatusAndCustomerIdAndProductId(true, customer.get().getId(), p.getId()))).toList();
                return new PageImpl<>(productList, pageResult.getPageable(), pageResult.getTotalElements());
            }
        }

        return new PageImpl<>(pageResult.getContent().stream()
                .map(ProductMapper.INSTANCE::toDTO)
                .collect(Collectors.toList()), pageResult.getPageable(), pageResult.getTotalElements());
    }

    @Override
    public Page<ProductDTO> getProductListByStoreId(boolean status, long storeId, List<Long> cateIds, List<Long> brandIds, List<Long> countryIds, String search, String sort, int page, int limit) {
        if (page < 0) throw new InvalidParameterException("Page number must not be less than zero!");
        if (limit < 1) throw new InvalidParameterException("Page size must not be less than one!");

        List<Sort.Order> order = new ArrayList<>();

        String userName = Common.userName;
        if (Common.role.equals("Store")) {
            Optional<Store> store = storeRepository.findStoreByIdAndStatus(storeId, true);
            if (store.isPresent()) {
                if (!store.get().getUserName().equals(userName)) {
                    throw new InvalidParameterException("The store's id is invalid!");
                }
            } else {
                throw new InvalidParameterException("Not found store's id!");
            }
        }

        Set<String> sourceFieldList = pagingService.getAllFields(Product.class);
        String[] subSort = sort.split(",");
        if (pagingService.checkPropertPresent(sourceFieldList, subSort[0])) {
            order.add(new Sort.Order(pagingService.getSortDirection(subSort[1]), transferProperty(subSort[0])));
        } else {
            throw new InvalidParameterException(subSort[0] + " is not a propertied of Product!");
        }

        Pageable pageable = PageRequest.of(page, limit).withSort(Sort.by(order));
        Page<Product> pageResult = productRepository.getProductListByStoreId(status, storeId, cateIds, brandIds, countryIds, search, pageable);

        return new PageImpl<>(pageResult.getContent().stream()
                .map(ProductMapper.INSTANCE::toDTO)
                .collect(Collectors.toList()), pageResult.getPageable(), pageResult.getTotalElements());
    }

    @Override
    public ProductExtraDTO getProductById(boolean status, long productId) {
        Optional<Product> product = productRepository.getProductByStatusAndId(status, productId);
        if (product.isPresent()) {
            ProductExtraDTO productExtraDTO = ProductMapper.INSTANCE.toExtraDTO(product.get());

            if (Common.role.equals("Customer")) {
                Optional<Customer> customer = customerRepository.findCustomerByUserNameAndStatus(Common.userName, true);
                customer.ifPresent(value -> productExtraDTO.setFavor(wishlistProductRepository.existsByStatusAndCustomerIdAndProductId(true, value.getId(), productExtraDTO.getId())));
            }

            productExtraDTO.setNumOfSold(detailRepository.getNumberOfSoldProduct(true, productExtraDTO.getId()).orElse(0));
            return productExtraDTO;
        }
        return null;
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
