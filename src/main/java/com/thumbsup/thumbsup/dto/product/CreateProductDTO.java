package com.thumbsup.thumbsup.dto.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateProductDTO implements Serializable {
    private String productName;
    private BigDecimal originalPrice;
    private BigDecimal salePrice;
    private BigDecimal discount;
    private BigDecimal shelfLife;
    private BigDecimal weight;
    private Integer quantity;
    private String description;
    private Boolean state;
    private Long storeId;
    private Long categoryId;
    private Long brandId;
    private Long countryId;
    private List<MultipartFile> imageList;
}
