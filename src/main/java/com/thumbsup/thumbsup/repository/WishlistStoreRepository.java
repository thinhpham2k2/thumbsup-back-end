package com.thumbsup.thumbsup.repository;

import com.thumbsup.thumbsup.entity.WishlistStore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WishlistStoreRepository extends JpaRepository<WishlistStore, Long> {
}
