package com.thumbsup.thumbsup.controller;

import com.thumbsup.thumbsup.service.interfaces.IReviewService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name = "Review API")
@RequestMapping("/api/v1/reviews")
@SecurityRequirement(name = "Authorization")
public class ReviewController {

    public static final String ADMIN = "ROLE_Admin";

    public static final String STORE = "ROLE_Store";

    public static final String CUSTOMER = "ROLE_Customer";

    private final IReviewService reviewService;
}
