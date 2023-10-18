package com.thumbsup.thumbsup.service.interfaces;

import com.thumbsup.thumbsup.dto.order.CreateOrderDTO;
import com.thumbsup.thumbsup.dto.order.OrderDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IOrderService {

    Page<OrderDTO> getOrderList(boolean status, List<Long> customerIds, List<Long> stateIds, List<Long> storeIds, String search, String sort, int page, int limit);

    OrderDTO getOrderById(long id);

    OrderDTO getOrderByIdForCustomer(long id, long customerId);

    OrderDTO getOrderByIdForStore(long id, long storeId);

    void createOrder(CreateOrderDTO create, boolean isPaid, String token);
}
