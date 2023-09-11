package com.thumbsup.thumbsup.repository;

import com.thumbsup.thumbsup.entity.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {

    @Query("SELECT SUM(d.quantity) FROM OrderDetail d " +
            "WHERE d.status = ?1 " +
            "AND d.product.id = ?2")
    Optional<Integer> getNumberOfSoldProduct(boolean status, long productId);
}
