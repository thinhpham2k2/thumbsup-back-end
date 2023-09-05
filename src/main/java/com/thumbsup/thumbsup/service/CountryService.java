package com.thumbsup.thumbsup.service;

import com.thumbsup.thumbsup.repository.CountryRepository;
import com.thumbsup.thumbsup.service.interfaces.ICountryService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class CountryService implements ICountryService {
    private final CountryRepository countryRepository;
}
