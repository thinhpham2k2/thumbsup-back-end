package com.thumbsup.thumbsup.service.interfaces;

import com.thumbsup.thumbsup.dto.order.OrderDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IOrderService {

    Page<OrderDTO> getOrderList(boolean status, List<Long> customerIds, List<Long> stateIds, List<Long> storeIds, String search, String sort, int page, int limit);
}
