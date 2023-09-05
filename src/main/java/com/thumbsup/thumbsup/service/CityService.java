package com.thumbsup.thumbsup.service;

import com.thumbsup.thumbsup.repository.CityRepository;
import com.thumbsup.thumbsup.service.interfaces.ICityService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class CityService implements ICityService {
    private final CityRepository cityRepository;
}
