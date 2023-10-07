package com.thumbsup.thumbsup.service;

import com.thumbsup.thumbsup.common.Common;
import com.thumbsup.thumbsup.dto.wishlist.WishlistStoreDTO;
import com.thumbsup.thumbsup.entity.Category;
import com.thumbsup.thumbsup.entity.WishlistStore;
import com.thumbsup.thumbsup.mapper.WishlistStoreMapper;
import com.thumbsup.thumbsup.repository.CategoryRepository;
import com.thumbsup.thumbsup.repository.ReviewRepository;
import com.thumbsup.thumbsup.repository.WishlistStoreRepository;
import com.thumbsup.thumbsup.service.interfaces.IWishlistStoreService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;

@Service
@Transactional
@RequiredArgsConstructor
public class WishlistStoreService implements IWishlistStoreService {

    private final WishlistStoreRepository wishlistStoreRepository;

    private final ReviewRepository reviewRepository;

    private final CategoryRepository categoryRepository;

    @Override
    public List<WishlistStoreDTO> getWishlistStore(boolean status) {
        return wishlistStoreRepository.getAllByStatusAndCustomerUserNameAndStoreStatus(status, Common.userName, status)
                .stream().filter(distinctByKey(WishlistStore::getStore)).map(WishlistStoreMapper.INSTANCE::toDTO)
                .peek(w -> {
                    int count = reviewRepository.countAllByProductStoreIdAndStatus(w.getStoreId(), true).orElse(0);
                    w.setFavor(true);
                    w.setNumOfRating(count);
                    w.setNumOfFollowing(wishlistStoreRepository.countAllByStatusAndStoreId(true, w.getStoreId()).orElse(0));
                    w.setRating(count == 0 ? BigDecimal.ZERO : reviewRepository.sumRatingReviewByStore(true, w.getStoreId()).orElse(BigDecimal.ZERO)
                            .divide(new BigDecimal(count), 2, RoundingMode.HALF_DOWN));
                    w.setCateList(categoryRepository
                            .getDistinctByProductListStoreIdAndProductListStatusAndStatus(w.getStoreId(), true, true)
                            .stream().map(Category::getCategory).toList());
                }).toList();
    }

    private static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Set<Object> seen = ConcurrentHashMap.newKeySet();
        return t -> seen.add(keyExtractor.apply(t));
    }
}
