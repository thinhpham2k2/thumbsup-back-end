package com.thumbsup.thumbsup.repository;

import com.thumbsup.thumbsup.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Optional<Customer> findCustomerByUserNameAndStatus(String userName, boolean status);
}
