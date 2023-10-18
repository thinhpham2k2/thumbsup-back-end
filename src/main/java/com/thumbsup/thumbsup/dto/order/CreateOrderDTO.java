package com.thumbsup.thumbsup.dto.order;

import com.thumbsup.thumbsup.validation.interfaces.CustomerConstraint;
import com.thumbsup.thumbsup.validation.interfaces.DetailConstraint;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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
public class CreateOrderDTO implements Serializable {
    @NotNull(message = "Amount is required")
    private BigDecimal amount;

    @CustomerConstraint
    @NotNull(message = "Customer's id is required")
    private Long customerId;

    private Boolean state;

    @Valid
    @DetailConstraint
    @NotEmpty(message = "List of order's detail is required")
    @NotNull(message = "Order's detail is required")
    private List<CreateOrderDetailDTO> orderDetailList;
}
