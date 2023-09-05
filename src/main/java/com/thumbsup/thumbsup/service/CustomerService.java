package com.thumbsup.thumbsup.service;

import com.thumbsup.thumbsup.repository.CustomerRepository;
import com.thumbsup.thumbsup.service.interfaces.ICustomerService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class CustomerService implements ICustomerService {
    private final CustomerRepository customerRepository;
}
