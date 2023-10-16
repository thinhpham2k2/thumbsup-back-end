package com.thumbsup.thumbsup.service.interfaces;

import com.thumbsup.thumbsup.dto.wishlist.UpdateWishlistProductDTO;
import com.thumbsup.thumbsup.dto.wishlist.WishlistProductDTO;

import java.util.List;

public interface IWishlistProductService {

    List<WishlistProductDTO> getWishlistProduct(boolean status);

    WishlistProductDTO updateWishlistProduct(UpdateWishlistProductDTO update);
}
