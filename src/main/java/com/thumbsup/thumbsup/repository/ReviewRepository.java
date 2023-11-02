package com.thumbsup.thumbsup.repository;

import com.thumbsup.thumbsup.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    Optional<Integer> countAllByProductStoreIdAndStatus(long storeId, boolean status);

    @Query("SELECT SUM(r.rating) FROM Review r " +
            "WHERE r.status = ?1 " +
            "AND r.product.store.id = ?2")
    Optional<BigDecimal> sumRatingReviewByStore(boolean status, long storeId);

    @Query("SELECT r FROM Review r " +
            "WHERE r.status = ?1 " +
            "AND r.product.id = ?2 " +
            "AND (r.customer.fullName LIKE %?3% " +
            "OR r.product.productName LIKE %?3% " +
            "OR r.comment LIKE %?3%)")
    Page<Review> getReviewListByProductId(boolean status, Long productId, String search, Pageable pageable);

    boolean existsByCustomer_IdAndProduct_IdAndStatus(long customerId, long productId, boolean status);
}
