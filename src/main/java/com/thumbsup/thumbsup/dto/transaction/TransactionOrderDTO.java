package com.thumbsup.thumbsup.dto.transaction;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionOrderDTO implements Serializable {
    private Long id;
    private String zpTransToken;
    private BigDecimal amount;
    private LocalDateTime dateCreated;
    private Boolean state;
    private Boolean status;
    private Long orderId;
    private Long storeId;
    private String storeName;
}
