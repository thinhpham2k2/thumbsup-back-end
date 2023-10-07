package com.thumbsup.thumbsup.service.interfaces;

import com.thumbsup.thumbsup.dto.ads.AdvertisementDTO;
import com.thumbsup.thumbsup.dto.store.StoreExtraDTO;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.List;

public interface IAdvertisementService {

    Page<StoreExtraDTO> getAdvertisementStoreList(boolean status, List<Long> storeIds, LocalDateTime dateNow, String search, String sort, int page, int limit);

    Page<AdvertisementDTO> getAdvertisementList(boolean status, List<Long> storeIds, LocalDateTime dateNow, String search, String sort, int page, int limit);
}
