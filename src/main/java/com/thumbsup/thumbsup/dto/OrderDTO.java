package com.thumbsup.thumbsup.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO implements Serializable {
    private Long id;
    private BigDecimal amount;
    private LocalDateTime dateCreated;
    private Boolean state;
    private Long stateOrderId;
    private String stateName;
    private Long customerId;
    private String customerName;
    private List<OrderStoreDTO> orderStoreList;
    private Boolean status;
}
