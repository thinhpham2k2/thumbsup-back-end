package com.thumbsup.thumbsup.service;

import com.thumbsup.thumbsup.common.Common;
import com.thumbsup.thumbsup.dto.wishlist.WishlistProductDTO;
import com.thumbsup.thumbsup.entity.WishlistProduct;
import com.thumbsup.thumbsup.mapper.WishlistProductMapper;
import com.thumbsup.thumbsup.repository.WishlistProductRepository;
import com.thumbsup.thumbsup.service.interfaces.IWishlistProductService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;

@Service
@Transactional
@RequiredArgsConstructor
public class WishlistProductService implements IWishlistProductService {

    private final WishlistProductRepository wishlistProductRepository;

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
