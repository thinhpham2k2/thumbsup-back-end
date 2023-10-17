package com.thumbsup.thumbsup.repository;

import com.thumbsup.thumbsup.entity.State;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StateRepository extends JpaRepository<State, Long> {

    @Query("SELECT s FROM State s " +
            "WHERE s.status = ?1 " +
            "AND (s.state LIKE %?2% " +
            "OR s.description LIKE %?2%)")
    Page<State> getStatesByStatus(boolean status, String search, Pageable pageable);

    Optional<State> findByIdAndStatus(long id, boolean status);
}
