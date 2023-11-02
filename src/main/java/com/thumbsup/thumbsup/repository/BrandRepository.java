package com.thumbsup.thumbsup.repository;

import com.thumbsup.thumbsup.entity.Brand;
import org.hibernate.sql.ast.tree.expression.Collation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Long> {

    @Query("SELECT b FROM Brand b " +
            "WHERE b.status = ?1 " +
            "AND b.brand LIKE %?2%")
    Page<Brand> getBrandsByStatus(boolean status, String search, Pageable pageable);

    Optional<Brand> findByIdAndStatus(long id, boolean status);
}
