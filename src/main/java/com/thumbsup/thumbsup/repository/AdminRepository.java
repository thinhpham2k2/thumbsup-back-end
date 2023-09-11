package com.thumbsup.thumbsup.repository;

import com.thumbsup.thumbsup.entity.Admin;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {

    Optional<Admin> findAdminByUserNameAndStatus(String userName, boolean status);

    Optional<Admin> findAdminByIdAndStatus(long adminId, boolean status);

    @Query("SELECT a FROM Admin a " +
            "WHERE a.status = ?1 " +
            "AND (a.fullName LIKE %?2% " +
            "OR a.phone LIKE %?2% " +
            "OR a.email LIKE %?2% " +
            "OR a.userName LIKE %?2%)")
    Page<Admin> getAdminList(boolean status, String search, Pageable pageable);
}
