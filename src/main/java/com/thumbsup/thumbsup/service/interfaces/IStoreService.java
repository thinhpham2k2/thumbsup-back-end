package com.thumbsup.thumbsup.service.interfaces;

import com.thumbsup.thumbsup.dto.store.CreateStoreDTO;
import com.thumbsup.thumbsup.dto.store.StoreDTO;
import com.thumbsup.thumbsup.dto.store.StoreExtraDTO;
import com.thumbsup.thumbsup.dto.store.UpdateStoreDTO;
import com.thumbsup.thumbsup.entity.Store;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IStoreService {

    Page<StoreDTO> getStoreList(boolean status, List<Long> cityIds, String search, String sort, int page, int limit);

    StoreExtraDTO getStoreById(boolean status, long storeId);

    boolean checkByUsername(String userName);

    StoreExtraDTO entityToExtra(Store entity);

    StoreExtraDTO createStore(CreateStoreDTO create);

    StoreExtraDTO updateStore(UpdateStoreDTO update, Long id);

    void deleteStore(Long id);
}
