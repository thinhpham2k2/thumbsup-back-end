package com.thumbsup.thumbsup.dto.chart;

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
public class TitleAdminDTO implements Serializable {
    private Long totalOfOrder;
    private Long totalOfCustomer;
    private Long totalOfStore;
    private BigDecimal profit;
}
