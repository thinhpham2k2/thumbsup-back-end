package com.thumbsup.thumbsup.repository;

import com.thumbsup.thumbsup.entity.Request;
import org.hibernate.sql.ast.tree.expression.Collation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RequestRepository extends JpaRepository<Request, Long> {

    @Query("SELECT r FROM Request r " +
            "WHERE r.status = ?1 " +
            "AND r.store.status = ?1 " +
            "AND (:#{#storeIds.size()} = 0 OR r.store.id IN ?2) " +
            "AND (r.store.storeName LIKE %?3% " +
            "OR r.note LIKE %?3%)")
    Page<Request> getRequestList(boolean status, List<Long> storeIds, String search, Pageable pageable);

    @Query("SELECT r FROM Request r " +
            "WHERE r.status = ?1 " +
            "AND r.store.id = ?2 " +
            "AND (r.store.storeName LIKE %?3% " +
            "OR r.note LIKE %?3%)")
    Page<Request> getRequestListByStoreId(boolean status, long storeId, String search, Pageable pageable);

    Optional<Request> findByIdAndStatus(long id, boolean status);

    boolean existsByStatusAndStore_Id(boolean status, long storeId);
}
