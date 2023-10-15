package com.thumbsup.thumbsup.repository;

import com.thumbsup.thumbsup.entity.Brand;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Long> {

    Page<Brand> getBrandsByStatus(boolean status, Pageable pageable);

    Optional<Brand> findByIdAndStatus(long id, boolean status);
}
