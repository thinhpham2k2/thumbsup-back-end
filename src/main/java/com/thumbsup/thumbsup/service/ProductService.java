package com.thumbsup.thumbsup.service;

import com.thumbsup.thumbsup.repository.ProductRepository;
import com.thumbsup.thumbsup.service.interfaces.IProductService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductService implements IProductService {
    private final ProductRepository productRepository;
}
