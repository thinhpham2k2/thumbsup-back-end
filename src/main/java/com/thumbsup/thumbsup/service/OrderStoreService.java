package com.thumbsup.thumbsup.service;

import com.thumbsup.thumbsup.repository.OrderStoreRepository;
import com.thumbsup.thumbsup.service.interfaces.IOrderStoreService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderStoreService implements IOrderStoreService {
    private final OrderStoreRepository orderStoreRepository;
}
