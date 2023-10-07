package com.thumbsup.thumbsup.service.interfaces;

import com.thumbsup.thumbsup.dto.store.StoreDTO;
import com.thumbsup.thumbsup.dto.store.StoreExtraDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IStoreService {

    Page<StoreDTO> getStoreList(boolean status, List<Long> cityIds, String search, String sort, int page, int limit);

    StoreExtraDTO getStoreById(boolean status, long storeId);
}
