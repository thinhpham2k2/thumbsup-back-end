package com.thumbsup.thumbsup.service;

import com.thumbsup.thumbsup.repository.BrandRepository;
import com.thumbsup.thumbsup.service.interfaces.IBrandService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class BrandService implements IBrandService {
    private final BrandRepository brandRepository;
}
