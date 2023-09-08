package com.thumbsup.thumbsup.repository;

import com.thumbsup.thumbsup.entity.TransactionOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionOrderRepository extends JpaRepository<TransactionOrder, Long> {
}
