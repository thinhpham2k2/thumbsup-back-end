package com.thumbsup.thumbsup.repository;

import com.thumbsup.thumbsup.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Query("SELECT r FROM Review r " +
            "WHERE r.status = ?1 " +
            "AND (:#{#productIds.size()} = 0 OR r.product.id IN ?2) " +
            "AND (r.customer.fullName LIKE %?3% " +
            "AND r.product.productName LIKE %?3% " +
            "OR r.comment LIKE %?3%)")
    Page<Review> getReviewList(boolean status, List<Long> productIds, String search, Pageable pageable);

    @Query("SELECT r FROM Review r " +
            "WHERE r.status = ?1 " +
            "AND (r.product.id = ?2) " +
            "AND (r.customer.fullName LIKE %?3% " +
            "AND r.product.productName LIKE %?3% " +
            "OR r.comment LIKE %?3%)")
    Page<Review> getReviewListByProductId(boolean status, Long productId, String search, Pageable pageable);
}
