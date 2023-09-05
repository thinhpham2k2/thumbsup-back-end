package com.thumbsup.thumbsup.service;

import com.thumbsup.thumbsup.repository.OrderDetailRepository;
import com.thumbsup.thumbsup.service.interfaces.IOrderDetailService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderDetailService implements IOrderDetailService {
    private final OrderDetailRepository detailRepository;
}
