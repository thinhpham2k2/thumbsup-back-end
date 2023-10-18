package com.thumbsup.thumbsup.service.interfaces;

import com.thumbsup.thumbsup.dto.review.CreateReviewDTO;
import com.thumbsup.thumbsup.dto.review.ReviewDTO;
import org.springframework.data.domain.Page;

public interface IReviewService {

    Page<ReviewDTO> getReviewListByProductId(boolean status, Long productId, String search, String sort, int page, int limit);

    ReviewDTO createReview(CreateReviewDTO create);
}
