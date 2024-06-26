package com.thumbsup.thumbsup.dto.product;

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
public class ProductExtraDTO implements Serializable {
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
    private Integer numOfSold;
    private String description;
    private Boolean favor;
    private Boolean state;
    private Long storeId;
    private String storeLogo;
    private String storeName;
    private String storeAddress;
    private Long categoryId;
    private String categoryName;
    private Long brandId;
    private String brandName;
    private Long countryId;
    private String countryName;
    private List<String> imageList;
    private Boolean status;
}
