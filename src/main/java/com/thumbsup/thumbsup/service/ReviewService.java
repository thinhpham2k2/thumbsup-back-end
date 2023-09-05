package com.thumbsup.thumbsup.service;

import com.thumbsup.thumbsup.repository.ReviewRepository;
import com.thumbsup.thumbsup.service.interfaces.IReviewService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class ReviewService implements IReviewService {
    private final ReviewRepository reviewRepository;
}
