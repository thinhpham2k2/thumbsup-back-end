package com.thumbsup.thumbsup.dto.order;

import com.thumbsup.thumbsup.dto.state.StateDetailDTO;
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
    private Long customerId;
    private String customerName;
    private Long storeId;
    private String storeName;
    private Boolean state;
    private Boolean status;
    private String stateCurrent;
    private List<OrderDetailDTO> orderDetailList;
    private List<StateDetailDTO> stateDetailList;
}
