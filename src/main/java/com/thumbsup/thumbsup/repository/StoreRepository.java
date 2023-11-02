package com.thumbsup.thumbsup.repository;

import com.thumbsup.thumbsup.entity.Store;
import org.hibernate.sql.ast.tree.expression.Collation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {

    Optional<Store> findStoreByUserNameAndStatus(String userName, boolean status);

    Optional<Store> findStoreByEmailAndStatus(String email, boolean status);

    Optional<Store> findStoreByStatusAndId(boolean status, long storeId);

    @Query("SELECT s FROM Store s " +
            "WHERE s.status = ?1 " +
            "AND (:#{#cityIds.size()} = 0 OR s.city.id IN ?2) " +
            "AND (s.city.cityName LIKE %?3% " +
            "OR s.userName LIKE %?3% " +
            "OR s.storeName LIKE %?3% " +
            "OR s.email LIKE %?3% " +
            "OR s.phone LIKE %?3% " +
            "OR s.address LIKE %?3% " +
            "OR s.description LIKE %?3%)")
    Page<Store> getStoreList(boolean status, List<Long> cityIds, String search, Pageable pageable);

    Long countAllByStatus(Boolean status);

    @Query("SELECT s.balance FROM Store s " +
            "WHERE s.status = ?1 " +
            "AND s.id = ?2")
    BigDecimal getBalanceByStoreId(Boolean status, Long storeId);
}
