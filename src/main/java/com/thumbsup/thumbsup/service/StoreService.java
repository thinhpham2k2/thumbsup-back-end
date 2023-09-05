package com.thumbsup.thumbsup.service;

import com.thumbsup.thumbsup.repository.StoreRepository;
import com.thumbsup.thumbsup.service.interfaces.IStoreService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class StoreService implements IStoreService {
    private final StoreRepository storeRepository;
}
