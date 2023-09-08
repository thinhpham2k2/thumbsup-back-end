package com.thumbsup.thumbsup.service;

import com.thumbsup.thumbsup.repository.TransactionOrderRepository;
import com.thumbsup.thumbsup.service.interfaces.ITransactionOrderService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class TransactionOrderService implements ITransactionOrderService {
    private final TransactionOrderRepository transactionOrderRepository;
}
