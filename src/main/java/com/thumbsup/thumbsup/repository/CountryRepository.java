package com.thumbsup.thumbsup.repository;

import com.thumbsup.thumbsup.entity.Country;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CountryRepository extends JpaRepository<Country, Long> {

    @Query("SELECT c FROM Country c " +
            "WHERE c.status = ?1 " +
            "AND (c.country LIKE %?2% " +
            "OR c.description LIKE %?2%)")
    Page<Country> getCountriesByStatus(boolean status, String search, Pageable pageable);

    Optional<Country> findByIdAndStatus(long id, boolean status);
}
