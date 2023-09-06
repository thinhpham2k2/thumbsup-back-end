package com.thumbsup.thumbsup.dto;

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
public class OrderStoreDTO implements Serializable {
    private Long id;
    private BigDecimal amount;
    private BigDecimal shippingFee;
    private Boolean isPaid;
    private String note;
    private String code;
    private String address;
    private Boolean state;
    private Long orderId;
    private List<StateDetailDTO> stateDetailList;
    private List<OrderDetailDTO> orderDetailList;
    private Boolean status;
}
