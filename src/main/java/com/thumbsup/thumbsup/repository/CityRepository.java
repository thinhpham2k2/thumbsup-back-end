package com.thumbsup.thumbsup.repository;

import com.thumbsup.thumbsup.entity.City;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {

    Page<City> getCitiesByStatus(boolean status, Pageable pageable);

    Optional<City> findByIdAndStatus(long id, boolean status);
}
