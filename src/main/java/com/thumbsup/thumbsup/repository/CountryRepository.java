package com.thumbsup.thumbsup.repository;

import com.thumbsup.thumbsup.entity.Country;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CountryRepository extends JpaRepository<Country, Long> {

    Page<Country> getCountriesByStatus(boolean status, Pageable pageable);

    Optional<Country> findByIdAndStatus(long id, boolean status);
}
