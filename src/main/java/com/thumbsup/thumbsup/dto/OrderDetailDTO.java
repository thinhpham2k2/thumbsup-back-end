package com.thumbsup.thumbsup.dto;

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
public class OrderDetailDTO implements Serializable {
    private Long id;
    private BigDecimal originalPrice;
    private BigDecimal discount;
    private BigDecimal salePrice;
    private Integer quantity;
    private BigDecimal amount;
    private Boolean state;
    private Long orderId;
    private Long productId;
    private String productName;
    private Boolean status;
}
