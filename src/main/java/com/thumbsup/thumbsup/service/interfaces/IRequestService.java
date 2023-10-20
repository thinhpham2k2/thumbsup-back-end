package com.thumbsup.thumbsup.service.interfaces;

import com.thumbsup.thumbsup.dto.request.CreateRequestDTO;
import com.thumbsup.thumbsup.dto.request.RequestDTO;
import com.thumbsup.thumbsup.dto.request.UpdateRequestDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IRequestService {

    Page<RequestDTO> getRequestList(boolean status, List<Long> storeIds, String search, String sort, int page, int limit);

    Page<RequestDTO> getRequestListByStoreId(boolean status, long storeId, String search, String sort, int page, int limit);

    RequestDTO getRequestById(long id);

    RequestDTO createRequest(CreateRequestDTO create);

    RequestDTO updateRequest(long id, UpdateRequestDTO update);

    void deleteRequest(long id);
}
