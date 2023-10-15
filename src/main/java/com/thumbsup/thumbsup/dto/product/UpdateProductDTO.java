package com.thumbsup.thumbsup.dto.product;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.thumbsup.thumbsup.validation.interfaces.BrandConstraint;
import com.thumbsup.thumbsup.validation.interfaces.CategoryConstraint;
import com.thumbsup.thumbsup.validation.interfaces.CountryConstraint;
import com.thumbsup.thumbsup.validation.interfaces.StoreConstraint;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(value = {"validSalePrice"})
public class UpdateProductDTO implements Serializable {
    @NotNull(message = "Product's name is required!")
    @Size(min = 2, max = 255, message = "The length of product's name is from 2 to 255 characters")
    private String productName;

    @Range(min = 0, max = 100000000, message = "Original price must be a positive number")
    private BigDecimal originalPrice;

    private BigDecimal salePrice;

    @Range(min = 0, max = 100, message = "Discount must be between 0 and 100 percent")
    private BigDecimal discount;

    @Range(min = 0, max = 120, message = "Shelf life must be between 0 and 120 months")
    private BigDecimal shelfLife;

    @Range(min = 0, max = 100, message = "Weight must be between 0 and 100 kg")
    private BigDecimal weight;

    @Range(min = 0, max = 100000000, message = "Quantity price must be a positive number")
    private Integer quantity;

    @NotNull(message = "Description is required!")
    @Size(min = 5, max = 4000, message = "The length of description is from 5 to 4000 characters")
    private String description;

    private Boolean state;

    @StoreConstraint
    @NotNull(message = "Store's id is required")
    private Long storeId;

    @CategoryConstraint
    @NotNull(message = "Category's id is required")
    private Long categoryId;

    @BrandConstraint
    @NotNull(message = "Brand's id is required")
    private Long brandId;

    @CountryConstraint
    @NotNull(message = "Country's id is required")
    private Long countryId;

    @AssertTrue(message = "Invalid sale price")
    public boolean isValidSalePrice() {
        return salePrice.compareTo(
                originalPrice.multiply(
                        BigDecimal.ONE.subtract(
                                discount.divide(new BigDecimal(100), 3, RoundingMode.HALF_UP)))) == 0;
    }
}
