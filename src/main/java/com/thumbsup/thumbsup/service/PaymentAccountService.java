package com.thumbsup.thumbsup.service;

import com.thumbsup.thumbsup.repository.PaymentAccountRepository;
import com.thumbsup.thumbsup.service.interfaces.IPaymentAccountService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class PaymentAccountService implements IPaymentAccountService {
    private final PaymentAccountRepository paymentAccountRepository;
}
