package com.thumbsup.thumbsup.service.interfaces;

import com.thumbsup.thumbsup.dto.WishlistProductDTO;

import java.util.List;

public interface IWishlistProductService {

    List<WishlistProductDTO> getWishlistProduct(boolean status);
}
