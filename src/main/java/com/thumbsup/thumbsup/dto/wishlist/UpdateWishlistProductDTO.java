package com.thumbsup.thumbsup.dto.wishlist;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateWishlistProductDTO implements Serializable {
    private Long customerId;
    private Long productId;
}
