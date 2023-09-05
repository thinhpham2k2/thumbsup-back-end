package com.thumbsup.thumbsup.repository;

import com.thumbsup.thumbsup.entity.State;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StateRepository extends JpaRepository<State, Long> {
}
