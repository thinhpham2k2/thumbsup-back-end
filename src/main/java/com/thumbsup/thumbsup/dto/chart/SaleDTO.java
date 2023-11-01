package com.thumbsup.thumbsup.dto.chart;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SaleDTO implements Serializable {
    private LocalDate date;
    private BigDecimal amount;
    private Long numberOfOrder;
}
