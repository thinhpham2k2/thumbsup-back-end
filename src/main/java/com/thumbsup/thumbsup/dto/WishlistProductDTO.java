package com.thumbsup.thumbsup.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WishlistProductDTO implements Serializable {
    private Long id;
    private Long customerId;
    private String customerName;
    private Long productId;
    private String productName;
    private Boolean status;
}
