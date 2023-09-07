package com.thumbsup.thumbsup.repository;

import com.thumbsup.thumbsup.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {

    Optional<Admin> findAdminByUserNameAndStatus(String userName, boolean status);
}
