package com.thumbsup.thumbsup.service;

import com.thumbsup.thumbsup.repository.WishlistStoreRepository;
import com.thumbsup.thumbsup.service.interfaces.IWishlistStoreService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class WishlistStoreService implements IWishlistStoreService {
    private final WishlistStoreRepository wishlistStoreRepository;
}
