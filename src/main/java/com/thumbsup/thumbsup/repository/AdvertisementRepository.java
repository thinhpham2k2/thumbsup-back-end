package com.thumbsup.thumbsup.repository;

import com.thumbsup.thumbsup.entity.Advertisement;
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
public interface AdvertisementRepository extends JpaRepository<Advertisement, Long> {

    @Query("SELECT a FROM Advertisement a " +
            "WHERE a.status = ?1 " +
            "AND a.store.status = ?1 " +
            "AND ?2 BETWEEN a.dateCreated AND a.dateExpired " +
            "AND (:#{#storeIds.size()} = 0 OR a.store.id IN ?3) " +
            "AND (a.store.storeName LIKE %?4% " +
            "OR a.adsName LIKE %?4% " +
            "OR a.description LIKE %?4%)")
    Page<Advertisement> getAdvertisementList
            (boolean status, LocalDateTime dateNow, List<Long> storeIds, String search, Pageable pageable, Collation collation);

    @Query("SELECT a FROM Advertisement a " +
            "WHERE a.status = ?1 " +
            "AND ?2 BETWEEN a.dateCreated AND a.dateExpired " +
            "AND a.store.id = ?3 " +
            "AND (a.store.storeName LIKE %?4% " +
            "OR a.adsName LIKE %?4% " +
            "OR a.description LIKE %?4%)")
    Page<Advertisement> getAdvertisementListByStoreId
            (boolean status, LocalDateTime dateNow, long storeId, String search, Pageable pageable, Collation collation);

    Optional<Advertisement> findByIdAndStatus(long id, boolean status);

    Optional<Advertisement> findFirstByStatusAndDateExpiredAfter(Boolean status, LocalDateTime dateExpired);

    @Query("SELECT SUM(a.clickCount) FROM Advertisement a " +
            "WHERE a.status = ?1 " +
            "AND a.store.id = ?2")
    Long countClickAdsByStoreId(Boolean status, Long storeId);
}
