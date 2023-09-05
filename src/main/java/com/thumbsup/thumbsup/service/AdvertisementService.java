package com.thumbsup.thumbsup.service;

import com.thumbsup.thumbsup.repository.AdvertisementRepository;
import com.thumbsup.thumbsup.service.interfaces.IAdvertisementService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class AdvertisementService implements IAdvertisementService {
    private final AdvertisementRepository advertisementRepository;
}
