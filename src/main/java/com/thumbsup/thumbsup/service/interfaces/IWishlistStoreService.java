package com.thumbsup.thumbsup.service.interfaces;

import com.thumbsup.thumbsup.dto.wishlist.WishlistStoreDTO;

import java.util.List;

public interface IWishlistStoreService {

    List<WishlistStoreDTO> getWishlistStore(boolean status);
}
