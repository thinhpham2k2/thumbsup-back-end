package com.thumbsup.thumbsup.repository;

import com.thumbsup.thumbsup.entity.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Optional<Customer> findCustomerByUserNameAndStatus(String userName, boolean status);

    Optional<Customer> findCustomerByEmailAndStatus(String email, boolean status);

    Optional<Customer> findCustomerByIdAndStatus(long customerId, boolean status);

    @Query("SELECT c FROM Customer c " +
            "WHERE c.status = ?1 " +
            "AND (:#{#cityIds.size()} = 0 OR c.city.id IN ?2) " +
            "AND (c.city.cityName LIKE %?3% " +
            "OR c.userName LIKE %?3% " +
            "OR c.fullName LIKE %?3% " +
            "OR c.email LIKE %?3% " +
            "OR c.avatar LIKE %?3% " +
            "OR c.address LIKE %?3% " +
            "OR c.phone LIKE %?3%)")
    Page<Customer> getCustomerList(boolean status, List<Long> cityIds, String search, Pageable pageable);
}
