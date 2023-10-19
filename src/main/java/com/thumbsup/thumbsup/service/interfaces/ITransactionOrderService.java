package com.thumbsup.thumbsup.service.interfaces;

import com.thumbsup.thumbsup.dto.transaction.TransactionOrderDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ITransactionOrderService {

    Page<TransactionOrderDTO> getTransactionList(boolean status, List<Long> storeIds, String search, String sort, int page, int limit);

    Page<TransactionOrderDTO> getTransactionListByStoreId(boolean status, long storeId, String search, String sort, int page, int limit);

    TransactionOrderDTO getTransactionById(long id);
}
