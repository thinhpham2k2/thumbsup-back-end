package com.thumbsup.thumbsup.service;

import com.thumbsup.thumbsup.repository.OrderRepository;
import com.thumbsup.thumbsup.service.interfaces.IOrderService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService implements IOrderService {
    private final OrderRepository orderRepository;
}
