package com.thumbsup.thumbsup.repository;

import com.thumbsup.thumbsup.entity.OrderStore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderStoreRepository extends JpaRepository<OrderStore, Long> {
}
