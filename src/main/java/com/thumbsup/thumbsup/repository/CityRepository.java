package com.thumbsup.thumbsup.repository;

import com.thumbsup.thumbsup.entity.City;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {

    @Query("SELECT c FROM City c " +
            "WHERE c.status = ?1 " +
            "AND c.cityName LIKE %?2%")
    Page<City> getCitiesByStatus(boolean status, String search, Pageable pageable);

    Optional<City> findByIdAndStatus(long id, boolean status);

    City findFirstByStatus(boolean status);
}
