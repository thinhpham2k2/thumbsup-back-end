package com.thumbsup.thumbsup.service.interfaces;

import com.thumbsup.thumbsup.dto.ReviewDTO;
import org.springframework.data.domain.Page;

public interface IReviewService {

    Page<ReviewDTO> getReviewListByProductId(boolean status, Long productId, String search, String sort, int page, int limit);
}
