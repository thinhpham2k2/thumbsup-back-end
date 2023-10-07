package com.thumbsup.thumbsup.dto.review;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDTO implements Serializable {
    private Long id;
    private String comment;
    private Integer rating;
    private LocalDateTime dateCreated;
    private Boolean state;
    private Long customerId;
    private String customerName;
    private Long productId;
    private String productName;
    private Boolean status;
}
