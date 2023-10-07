package com.thumbsup.thumbsup.dto.wishlist;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WishlistProductDTO implements Serializable {
    private Long id;
    private Long customerId;
    private String customerName;
    private Long storeId;
    private String storeName;
    private Long productId;
    private String productName;
    private String productImage;
    private BigDecimal rating;
    private Integer numOfRating;
    private Boolean favor;
    private Boolean status;
}
