package com.thumbsup.thumbsup.repository;

import com.thumbsup.thumbsup.entity.StateDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StateDetailRepository extends JpaRepository<StateDetail, Long> {
}
