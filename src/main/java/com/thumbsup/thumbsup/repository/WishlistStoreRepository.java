package com.thumbsup.thumbsup.repository;

import com.thumbsup.thumbsup.entity.WishlistStore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WishlistStoreRepository extends JpaRepository<WishlistStore, Long> {

    Boolean existsByStatusAndCustomerIdAndStoreId(boolean status, long customerId, long storeId);

    Optional<Integer> countAllByStatusAndStoreId(boolean status, long storeId);

    List<WishlistStore> getAllByStatusAndCustomerUserNameAndStoreStatus(boolean status, String userName, boolean storeStatus);
}
