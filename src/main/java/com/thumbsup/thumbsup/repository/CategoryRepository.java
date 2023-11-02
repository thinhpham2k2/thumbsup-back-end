package com.thumbsup.thumbsup.repository;

import com.thumbsup.thumbsup.entity.Category;
import org.hibernate.sql.ast.tree.expression.Collation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query("SELECT c FROM Category c " +
            "WHERE c.status = ?1 " +
            "AND c.category LIKE %?2%")
    Page<Category> getCategoriesByStatus(boolean status, String search, Pageable pageable, Collation collation);

    List<Category> getDistinctByProductListStoreIdAndProductListStatusAndStatus
            (long storeId, boolean productStatus, boolean cateStatus);

    Optional<Category> findByIdAndStatus(long id, boolean status);
}
