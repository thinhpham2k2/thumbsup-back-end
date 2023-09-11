package com.thumbsup.thumbsup.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WishlistStoreDTO implements Serializable {
    private Long id;
    private Long customerId;
    private String customerName;
    private Long storeId;
    private String storeName;
    private String storeImageCover;
    private String storeImageLogo;
    private Integer numOfFollowing;
    private Integer numOfRating;
    private BigDecimal rating;
    private Boolean favor;
    private List<String> cateList;
    private Boolean status;
}
