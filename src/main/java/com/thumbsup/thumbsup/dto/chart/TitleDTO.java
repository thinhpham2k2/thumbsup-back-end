package com.thumbsup.thumbsup.dto.chart;

import com.thumbsup.thumbsup.dto.ads.AdvertisementDTO;
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
public class TitleDTO implements Serializable {
    public Long totalOfOrder;
    public Long totalOfClick;
    public BigDecimal balance;
    public AdvertisementDTO ads;
}
