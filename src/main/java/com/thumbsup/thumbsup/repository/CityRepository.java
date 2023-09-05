package com.thumbsup.thumbsup.repository;

import com.thumbsup.thumbsup.entity.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {
}
