package com.thumbsup.thumbsup.repository;

import com.thumbsup.thumbsup.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    Page<Category> getCategoriesByStatus(boolean status, Pageable pageable);

    List<Category> getDistinctByProductListStoreIdAndProductListStatusAndStatus(long storeId, boolean productStatus, boolean cateStatus);
}
