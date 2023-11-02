package com.thumbsup.thumbsup.repository;

import com.thumbsup.thumbsup.entity.PaymentAccount;
import org.hibernate.sql.ast.tree.expression.Collation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentAccountRepository extends JpaRepository<PaymentAccount, Long> {

    @Query("SELECT p FROM PaymentAccount p " +
            "WHERE p.status = ?1 " +
            "AND p.store.status = ?1 " +
            "AND (:#{#storeIds.size()} = 0 OR p.store.id IN ?2) " +
            "AND (p.store.storeName LIKE %?3% " +
            "OR p.zpTransToken LIKE %?3%)")
    Page<PaymentAccount> getPaymentList(boolean status, List<Long> storeIds, String search, Pageable pageable);

    @Query("SELECT p FROM PaymentAccount p " +
            "WHERE p.status = ?1 " +
            "AND p.store.id = ?2 " +
            "AND (p.store.storeName LIKE %?3% " +
            "OR p.zpTransToken LIKE %?3%)")
    Page<PaymentAccount> getPaymentListByStoreId(boolean status, long storeId, String search, Pageable pageable);

    Optional<PaymentAccount> findByIdAndStatus(long id, boolean status);
}
