package com.thumbsup.thumbsup.service;

import com.thumbsup.thumbsup.common.Common;
import com.thumbsup.thumbsup.dto.wishlist.UpdateWishlistProductDTO;
import com.thumbsup.thumbsup.dto.wishlist.WishlistProductDTO;
import com.thumbsup.thumbsup.entity.Customer;
import com.thumbsup.thumbsup.entity.Product;
import com.thumbsup.thumbsup.entity.WishlistProduct;
import com.thumbsup.thumbsup.mapper.WishlistProductMapper;
import com.thumbsup.thumbsup.repository.CustomerRepository;
import com.thumbsup.thumbsup.repository.ProductRepository;
import com.thumbsup.thumbsup.repository.WishlistProductRepository;
import com.thumbsup.thumbsup.service.interfaces.IWishlistProductService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.InvalidParameterException;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;

@Service
@Transactional
@RequiredArgsConstructor
public class WishlistProductService implements IWishlistProductService {

    private final ProductRepository productRepository;

    private final CustomerRepository customerRepository;

    private final WishlistProductRepository wishlistProductRepository;

    @Override
    public WishlistProductDTO updateWishlistProduct(UpdateWishlistProductDTO update) {
        Optional<Product> product = productRepository.getProductByStatusAndId(true, update.getProductId());
        if (product.isPresent()) {
            Optional<Customer> customer = customerRepository.findCustomerByIdAndStatus(update.getCustomerId(), true);
            if (customer.isPresent()) {
                Optional<WishlistProduct> wishlist = wishlistProductRepository.findFirstByCustomer_IdAndProduct_Id(update.getCustomerId(), update.getProductId());
                if (wishlist.isPresent()) {
                    wishlist.get().setStatus(!wishlist.get().getStatus());
                    return WishlistProductMapper.INSTANCE.toDTO(wishlistProductRepository.save(wishlist.get()));
                } else {
                    return WishlistProductMapper.INSTANCE.toDTO(wishlistProductRepository.save(new WishlistProduct(null, true, customer.get(), product.get())));
                }
            } else {
                throw new InvalidParameterException("Not found customer");
            }
        } else {
            throw new InvalidParameterException("Not found product");
        }
    }

    @Override
    public List<WishlistProductDTO> getWishlistProduct(boolean status) {
        return wishlistProductRepository.getAllByStatusAndCustomerUserNameAndProductStatus(status, Common.userName, status)
                .stream().filter(distinctByKey(WishlistProduct::getProduct)).map(WishlistProductMapper.INSTANCE::toDTO)
                .peek(w -> w.setFavor(true)).distinct().toList();
    }

    private static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Set<Object> seen = ConcurrentHashMap.newKeySet();
        return t -> seen.add(keyExtractor.apply(t));
    }
}
