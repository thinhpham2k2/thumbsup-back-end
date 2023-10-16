package com.thumbsup.thumbsup.repository;

import com.thumbsup.thumbsup.entity.WishlistProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WishlistProductRepository extends JpaRepository<WishlistProduct, Long> {

    Optional<WishlistProduct> findFirstByCustomer_IdAndProduct_Id(long customerId, long productId);

    Boolean existsByStatusAndCustomerIdAndProductId(boolean status, long customerId, long productId);

    List<WishlistProduct> getAllByStatusAndCustomerUserNameAndProductStatus(boolean status, String userName, boolean productStatus);
}
