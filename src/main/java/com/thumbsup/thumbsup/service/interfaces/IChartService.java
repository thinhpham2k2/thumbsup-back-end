package com.thumbsup.thumbsup.service.interfaces;

import com.thumbsup.thumbsup.dto.chart.SaleDTO;
import com.thumbsup.thumbsup.dto.chart.TitleAdminDTO;
import com.thumbsup.thumbsup.dto.chart.TitleDTO;

import java.time.LocalDateTime;
import java.util.List;

public interface IChartService {

    TitleAdminDTO getTitle();

    TitleDTO getTitleByStoreId(Long storeId);

    List<SaleDTO> getSale(String sort, LocalDateTime fromDate, LocalDateTime toDate);

    List<SaleDTO> getSaleByStoreId(String sort, Long storeId, LocalDateTime fromDate, LocalDateTime toDate);
}
