package com.thumbsup.thumbsup.repository;

import com.thumbsup.thumbsup.entity.WishlistProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WishlistProductRepository extends JpaRepository<WishlistProduct, Long> {

    Boolean existsByStatusAndCustomerIdAndProductId(boolean status, long customerId, long productId);

    List<WishlistProduct> getAllByStatusAndCustomerUserNameAndProductStatus(boolean status, String userName, boolean productStatus);
}
