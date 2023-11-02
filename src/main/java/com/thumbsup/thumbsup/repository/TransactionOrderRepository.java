package com.thumbsup.thumbsup.repository;

import com.thumbsup.thumbsup.entity.TransactionOrder;
import org.hibernate.sql.ast.tree.expression.Collation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionOrderRepository extends JpaRepository<TransactionOrder, Long> {

    @Query("SELECT t FROM TransactionOrder t " +
            "WHERE t.status = ?1 " +
            "AND (:#{#storeIds.size()} = 0 OR t.store.id IN ?2) " +
            "AND (t.store.storeName LIKE %?3% " +
            "OR t.zpTransToken LIKE %?3%)")
    Page<TransactionOrder> getTransactionList
            (boolean status, List<Long> storeIds, String search, Pageable pageable, Collation collation);

    @Query("SELECT t FROM TransactionOrder t " +
            "WHERE t.status = ?1 " +
            "AND t.store.id = ?2 " +
            "AND (t.store.storeName LIKE %?3% " +
            "OR t.zpTransToken LIKE %?3%)")
    Page<TransactionOrder> getTransactionListByStoreId
            (boolean status, long storeId, String search, Pageable pageable, Collation collation);

    Optional<TransactionOrder> findByIdAndStatus(long id, boolean status);

    List<TransactionOrder> findAllByStatusAndDateCreatedBetween(Boolean status, LocalDateTime from, LocalDateTime to);

    List<TransactionOrder> findAllByStatusAndStore_IdAndDateCreatedBetween(Boolean status, Long storeId, LocalDateTime dateCreated, LocalDateTime dateCreated2);
}
