package com.thumbsup.thumbsup.dto.order;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.thumbsup.thumbsup.validation.interfaces.ProductConstraint;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;
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
@JsonIgnoreProperties(value = {"validAmount"})
public class CreateOrderDetailDTO implements Serializable {
    @NotNull(message = "Original price is required")
    private BigDecimal originalPrice;

    @NotNull(message = "Discount is required")
    private BigDecimal discount;

    @NotNull(message = "Sale price is required")
    private BigDecimal salePrice;

    @NotNull(message = "Quantity is required")
    private Integer quantity;

    @NotNull(message = "Amount is required")
    private BigDecimal amount;

    private Boolean state;

    @ProductConstraint
    @NotNull(message = "Product's id is required")
    private Long productId;

    @AssertTrue(message = "Invalid amount")
    public boolean isValidAmount() {
        return amount.compareTo(salePrice.multiply(new BigDecimal(quantity))) == 0;
    }
}
