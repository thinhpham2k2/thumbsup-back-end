package com.thumbsup.thumbsup.service;

import com.thumbsup.thumbsup.dto.ReviewDTO;
import com.thumbsup.thumbsup.entity.Review;
import com.thumbsup.thumbsup.mapper.ReviewMapper;
import com.thumbsup.thumbsup.repository.ReviewRepository;
import com.thumbsup.thumbsup.service.interfaces.IPagingService;
import com.thumbsup.thumbsup.service.interfaces.IReviewService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ReviewService implements IReviewService {

    private final IPagingService pagingService;

    private final ReviewRepository reviewRepository;

    @Override
    public Page<ReviewDTO> getReviewListByProductId(boolean status, Long productId, String search, String sort, int page, int limit) {
        if (page < 0) throw new InvalidParameterException("Page number must not be less than zero!");
        if (limit < 1) throw new InvalidParameterException("Page size must not be less than one!");

        List<Sort.Order> order = new ArrayList<>();
        Set<String> sourceFieldList = pagingService.getAllFields(Review.class);
        String[] subSort = sort.split(",");
        if (pagingService.checkPropertPresent(sourceFieldList, subSort[0])) {
            order.add(new Sort.Order(pagingService.getSortDirection(subSort[1]), transferProperty(subSort[0])));
        } else {
            throw new InvalidParameterException(subSort[0] + " is not a propertied of Review!");
        }

        Pageable pageable = PageRequest.of(page, limit).withSort(Sort.by(order));
        Page<Review> pageResult = reviewRepository.getReviewListByProductId(status, productId, search, pageable);

        if (CustomUserDetailsService.role.equals("Customer")) {
            List<ReviewDTO> reviewList = pageResult.stream().filter(r -> r.getState().equals(true)).map(ReviewMapper.INSTANCE::toDTO).toList();
            return new PageImpl<>(reviewList, pageResult.getPageable(), pageResult.getTotalElements());
        }

        return new PageImpl<>(pageResult.getContent().stream()
                .map(ReviewMapper.INSTANCE::toDTO)
                .collect(Collectors.toList()), pageResult.getPageable(), pageResult.getTotalElements());
    }

    private static String transferProperty(String property) {
        return switch (property) {
            case "customer" -> "customer.fullName";
            case "product" -> "product.productName";
            default -> property;
        };
    }
}
