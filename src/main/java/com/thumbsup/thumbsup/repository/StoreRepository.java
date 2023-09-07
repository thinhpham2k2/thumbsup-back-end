package com.thumbsup.thumbsup.repository;

import com.thumbsup.thumbsup.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {

    Optional<Store> findStoreByUserNameAndStatus(String userName, boolean status);
}
