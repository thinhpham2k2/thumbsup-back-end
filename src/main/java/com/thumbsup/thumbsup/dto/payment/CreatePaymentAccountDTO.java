package com.thumbsup.thumbsup.dto.payment;

import com.thumbsup.thumbsup.validation.interfaces.StoreConstraint;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreatePaymentAccountDTO implements Serializable {
    @NotEmpty(message = "Token is required")
    @NotNull(message = "Token is required")
    private String zpTransToken;

    @Range(min = 1, max = 1000000000, message = "Amount must be greater than 0")
    private BigDecimal amount;

    private Boolean state;

    @StoreConstraint
    @NotNull(message = "Store's id is required")
    private Long storeId;
}
