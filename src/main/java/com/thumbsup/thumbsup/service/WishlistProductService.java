package com.thumbsup.thumbsup.service;

import com.thumbsup.thumbsup.repository.WishlistProductRepository;
import com.thumbsup.thumbsup.service.interfaces.IWishlistProductService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class WishlistProductService implements IWishlistProductService {
    private final WishlistProductRepository wishlistProductRepository;
}
