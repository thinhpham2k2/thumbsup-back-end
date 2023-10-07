package com.thumbsup.thumbsup.dto.product;

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
public class ProductDTO implements Serializable {
    private Long id;
    private String productName;
    private BigDecimal originalPrice;
    private BigDecimal salePrice;
    private BigDecimal discount;
    private BigDecimal shelfLife;
    private BigDecimal weight;
    private BigDecimal rating;
    private Integer quantity;
    private Integer numOfRating;
    private String description;
    private Boolean favor;
    private Boolean state;
    private Long storeId;
    private String storeName;
    private Long categoryId;
    private String categoryName;
    private Long brandId;
    private String brandName;
    private Long countryId;
    private String countryName;
    private String imageCover;
    private Boolean status;
}
