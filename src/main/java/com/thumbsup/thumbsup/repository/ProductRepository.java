package com.thumbsup.thumbsup.repository;

import com.thumbsup.thumbsup.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Optional<Product> getProductByStatusAndId(boolean status, long productId);

    @Query("SELECT p FROM Product p " +
            "WHERE p.status = ?1 " +
            "AND p.store.status = ?1 " +
            "AND (:#{#storeIds.size()} = 0 OR p.store.id IN ?2) " +
            "AND (:#{#cateIds.size()} = 0 OR p.category.id IN ?3) " +
            "AND (:#{#brandIds.size()} = 0 OR p.brand.id IN ?4) " +
            "AND (:#{#countryIds.size()} = 0 OR p.country.id IN ?5) " +
            "AND (p.store.storeName LIKE %?6% " +
            "OR p.category.category LIKE %?6% " +
            "OR p.brand.brand LIKE %?6% " +
            "OR p.country.country LIKE %?6% " +
            "OR p.productName LIKE %?6% " +
            "OR p.description LIKE %?6%)" +
            "AND p.salePrice BETWEEN ?7 AND ?8")
    Page<Product> getProductList
            (boolean status, List<Long> storeIds, List<Long> cateIds, List<Long> brandIds, List<Long> countryIds,
             String search, BigDecimal priceStart, BigDecimal priceEnd, Pageable pageable);

    @Query("SELECT p FROM Product p " +
            "WHERE p.status = ?1 " +
            "AND p.store.id = ?2 " +
            "AND (:#{#cateIds.size()} = 0 OR p.category.id IN ?3) " +
            "AND (:#{#brandIds.size()} = 0 OR p.brand.id IN ?4) " +
            "AND (:#{#countryIds.size()} = 0 OR p.country.id IN ?5) " +
            "AND (p.category.category LIKE %?6% " +
            "OR p.brand.brand LIKE %?6% " +
            "OR p.country.country LIKE %?6% " +
            "OR p.productName LIKE %?6% " +
            "OR p.description LIKE %?6%)")
    Page<Product> getProductListByStoreId
            (boolean status, long storeId, List<Long> cateIds, List<Long> brandIds, List<Long> countryIds,
             String search, Pageable pageable);
}
