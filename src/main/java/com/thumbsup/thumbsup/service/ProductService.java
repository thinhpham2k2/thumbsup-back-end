package com.thumbsup.thumbsup.service;

import com.thumbsup.thumbsup.common.Common;
import com.thumbsup.thumbsup.dto.product.CreateProductDTO;
import com.thumbsup.thumbsup.dto.product.ProductDTO;
import com.thumbsup.thumbsup.dto.product.ProductExtraDTO;
import com.thumbsup.thumbsup.dto.product.UpdateProductDTO;
import com.thumbsup.thumbsup.entity.Customer;
import com.thumbsup.thumbsup.entity.Image;
import com.thumbsup.thumbsup.entity.Product;
import com.thumbsup.thumbsup.entity.Store;
import com.thumbsup.thumbsup.mapper.ProductMapper;
import com.thumbsup.thumbsup.repository.*;
import com.thumbsup.thumbsup.service.interfaces.IFileService;
import com.thumbsup.thumbsup.service.interfaces.IPagingService;
import com.thumbsup.thumbsup.service.interfaces.IProductService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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

    private final IFileService fileService;

    private final IPagingService pagingService;

    private final StoreRepository storeRepository;

    private final ImageRepository imageRepository;

    private final ProductRepository productRepository;

    private final CustomerRepository customerRepository;

    private final OrderDetailRepository detailRepository;

    private final WishlistProductRepository wishlistProductRepository;

    @Override
    public ProductExtraDTO createProduct(CreateProductDTO create) {
        Product product = ProductMapper.INSTANCE.createToEntity(create);

        boolean isCover = true;
        for (MultipartFile image : create.getImageList()) {
            try {
                String linkImage = fileService.upload(image);
                imageRepository.save(new Image(null, linkImage, isCover, true, product));
                isCover = false;
            } catch (Exception ignored) {
            }
        }

        return entityToExtra(productRepository.save(product));
    }

    @Override
    public ProductExtraDTO updateProduct(UpdateProductDTO update, Long id) {
        Optional<Product> product = productRepository.getProductByStatusAndId(true, id);
        if (product.isPresent()){
            Product pro = ProductMapper.INSTANCE.updateToEntity(update, product.get());
            return entityToExtra(productRepository.save(pro));
        } else {
            throw new InvalidParameterException("Not found product");
        }
    }

    @Override
    public void deleteProduct(Long id) {
        Optional<Product> product = productRepository.getProductByStatusAndId(true, id);
        if (product.isPresent()){
            product.get().setStatus(false);
            productRepository.save(product.get());
        } else {
            throw new InvalidParameterException("Not found product");
        }
    }

    @Override
    public ProductExtraDTO entityToExtra(Product entity) {
        ProductExtraDTO productExtraDTO = ProductMapper.INSTANCE.toExtraDTO(entity);

        if (Common.role.equals("Customer")) {
            Optional<Customer> customer = customerRepository.findCustomerByUserNameAndStatus(Common.userName, true);
            customer.ifPresent(value -> productExtraDTO.setFavor(wishlistProductRepository.existsByStatusAndCustomerIdAndProductId(true, value.getId(), productExtraDTO.getId())));
        }

        productExtraDTO.setNumOfSold(detailRepository.getNumberOfSoldProduct(true, productExtraDTO.getId()).orElse(0));
        return productExtraDTO;
    }

    @Override
    public Page<ProductDTO> getProductList(boolean status, List<Long> storeIds, List<Long> cateIds, List<Long> brandIds,
                                           List<Long> countryIds, String search, String sort, int page, int limit) {
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
    public Page<ProductDTO> getProductListByStoreId(boolean status, long storeId, List<Long> cateIds, List<Long> brandIds,
                                                    List<Long> countryIds, String search, String sort, int page, int limit) {
        if (page < 0) throw new InvalidParameterException("Page number must not be less than zero!");
        if (limit < 1) throw new InvalidParameterException("Page size must not be less than one!");

        List<Sort.Order> order = new ArrayList<>();

        String userName = Common.userName;
        Optional<Store> store = storeRepository.findStoreByIdAndStatus(storeId, true);
        if (store.isPresent()) {
            if (Common.role.equals("Store")) {
                Optional<Store> storeOptional = storeRepository.findStoreByUserNameAndStatus(userName, true);
                storeId = storeOptional.isPresent() ? storeOptional.get().getId() : storeId;
            }
        } else {
            throw new InvalidParameterException("Not found store's id!");
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
        return product.map(this::entityToExtra).orElse(null);
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
