package com.thumbsup.thumbsup.dto.review;

import com.thumbsup.thumbsup.validation.interfaces.CustomerConstraint;
import com.thumbsup.thumbsup.validation.interfaces.ProductConstraint;
import com.thumbsup.thumbsup.validation.interfaces.RatingConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateReviewDTO implements Serializable {
    private String comment;

    @RatingConstraint
    private Integer rating;

    private Boolean state;

    @CustomerConstraint
    private Long customerId;

    @ProductConstraint
    private Long productId;
}
