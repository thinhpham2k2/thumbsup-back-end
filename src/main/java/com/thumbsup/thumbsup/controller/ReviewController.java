package com.thumbsup.thumbsup.controller;

import com.thumbsup.thumbsup.dto.review.CreateReviewDTO;
import com.thumbsup.thumbsup.dto.review.ReviewDTO;
import com.thumbsup.thumbsup.service.interfaces.IReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

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

    @PostMapping("")
    @Secured({CUSTOMER})
    @Operation(summary = "Customer review product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created", content =
                    {@Content(mediaType = "application/json", schema =
                    @Schema(implementation = ReviewDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Fail", content =
                    {@Content(mediaType = "text/plain", schema = @Schema(implementation = String.class))}),
    })
    public ResponseEntity<?> createReview(@RequestBody @Validated CreateReviewDTO create)
            throws MethodArgumentTypeMismatchException {
        ReviewDTO review = reviewService.createReview(create);
        if (review != null) {
            return ResponseEntity.status(HttpStatus.OK).body(review);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Create fail");
        }
    }
}
