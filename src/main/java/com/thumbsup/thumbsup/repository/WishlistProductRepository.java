package com.thumbsup.thumbsup.repository;

import com.thumbsup.thumbsup.entity.WishlistProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WishlistProductRepository extends JpaRepository<WishlistProduct, Long> {

    Boolean existsByStatusAndCustomerIdAndProductId(boolean status, Long customerId, Long productId);
}
