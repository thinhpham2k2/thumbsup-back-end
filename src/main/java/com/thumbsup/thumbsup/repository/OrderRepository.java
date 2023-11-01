package com.thumbsup.thumbsup.repository;

import com.thumbsup.thumbsup.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("SELECT o FROM Order o " +
            "WHERE o.status = ?1 " +
            "AND (:#{#customerIds.size()} = 0 OR o.customer.id IN ?2) " +
            "AND (:#{#stateIds.size()} = 0 OR (SELECT MAX(s.state.id) FROM StateDetail s WHERE s.order = o) IN ?3) " +
            "AND (:#{#storeIds.size()} = 0 OR (SELECT MAX(d.product.store.id) FROM OrderDetail d WHERE d.order = o) IN ?4) " +
            "AND (o.customer.fullName LIKE %?5% " +
            "OR o.customer.email LIKE %?5% " +
            "OR o.customer.city.cityName LIKE %?5%)" +
            "GROUP BY o")
    Page<Order> getOrderList(boolean status, List<Long> customerIds, List<Long> stateIds, List<Long> storeIds, String search, Pageable pageable);

    Optional<Order> findByIdAndStatus(long id, boolean status);

    Optional<Order> findByIdAndCustomer_IdAndStatus(long id, long customerId, boolean status);

    @Query("SELECT o FROM Order o " +
            "WHERE o.id = ?1 " +
            "AND o.status = ?2 " +
            "AND (SELECT MAX(d.product.store.id) FROM OrderDetail d WHERE d.order = o) = ?3 ")
    Optional<Order> getOrderByIdAndStore(long id, boolean status, long storeId);

    Long countAllByStatus(Boolean status);

    @Query("SELECT COUNT(o) FROM Order o " +
            "WHERE o.status = ?1 " +
            "AND (SELECT MAX(d.product.store.id) FROM OrderDetail d WHERE d.order = o) = ?2 ")
    Long countAllByStatusAndStoreId(Boolean status, Long storeId);

    List<Order> findAllByStatusAndDateCreatedBetween(Boolean status, LocalDateTime from, LocalDateTime to);

    @Query("SELECT o FROM Order o " +
            "WHERE o.status = ?1 " +
            "AND o.dateCreated BETWEEN ?3 AND ?4 " +
            "AND (SELECT MAX(d.product.store.id) FROM OrderDetail d WHERE d.order = o) = ?2 ")
    List<Order> findByStatusAndDateCreatedBetween(Boolean status, Long storeId, LocalDateTime from, LocalDateTime to);
}
