package com.thumbsup.thumbsup.service;

import com.thumbsup.thumbsup.dto.order.OrderDTO;
import com.thumbsup.thumbsup.entity.Order;
import com.thumbsup.thumbsup.mapper.OrderMapper;
import com.thumbsup.thumbsup.repository.OrderRepository;
import com.thumbsup.thumbsup.service.interfaces.IOrderService;
import com.thumbsup.thumbsup.service.interfaces.IPagingService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.JpaSort;
import org.springframework.stereotype.Service;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService implements IOrderService {

    private final IPagingService pagingService;

    private final OrderRepository orderRepository;

    @Override
    public Page<OrderDTO> getOrderList(boolean status, List<Long> customerIds, List<Long> stateIds, List<Long> storeIds,
                                       String search, String sort, int page, int limit) {
        if (limit < 1) throw new InvalidParameterException("Page size must not be less than one!");
        if (page < 0) throw new InvalidParameterException("Page number must not be less than zero!");

        List<Sort.Order> order = new ArrayList<>();
        Set<String> sourceFieldList = pagingService.getAllFields(Order.class);
        String[] subSort = sort.split(",");
        if (pagingService.checkPropertPresent(sourceFieldList, subSort[0])) {
            order.add(new Sort.Order(pagingService.getSortDirection(subSort[1]), transferProperty(subSort[0])));
        } else if (subSort[0].equals("stateCurrent")) {
            order.addAll(sortCustom(pagingService.getSortDirection(subSort[1])));
        } else {
            throw new InvalidParameterException(subSort[0] + " is not a propertied of Order");
        }

        Pageable pageable = PageRequest.of(page, limit).withSort(Sort.by(order));
        Page<Order> pageResult = orderRepository.getOrderList(status, customerIds, stateIds, storeIds, search, pageable);

        return new PageImpl<>(pageResult.getContent().stream()
                .map(OrderMapper.INSTANCE::toDTO)
                .collect(Collectors.toList()), pageResult.getPageable(), pageResult.getTotalElements());
    }

    private String transferProperty(String property) {
        if (property.equals("customer")) {
            return "customer.fullName";
        }
        return property;
    }

    private List<Sort.Order> sortCustom(Sort.Direction direction) {
        return JpaSort.unsafe(direction, "(SELECT MAX(s.state.id) FROM StateDetail s WHERE s.order = o)")
                .stream().toList();
    }
}
