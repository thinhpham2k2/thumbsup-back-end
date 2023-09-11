package com.thumbsup.thumbsup.service.interfaces;

import com.thumbsup.thumbsup.dto.StoreDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IStoreService {

    Page<StoreDTO> getStoreList(boolean status, List<Long> cityIds, String search, String sort, int page, int limit);
}
