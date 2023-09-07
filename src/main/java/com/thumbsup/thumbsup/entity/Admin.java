package com.thumbsup.thumbsup.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Nationalized;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Admin")
@Table(name = "tbl_admin", uniqueConstraints = {@UniqueConstraint(name = "admin_user_name_unique", columnNames = "user_name"), @UniqueConstraint(name = "admin_user_email_unique", columnNames = "email"), @UniqueConstraint(name = "admin_user_phone_unique", columnNames = "phone")})
public class Admin implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "user_name", updatable = false, length = 50)
    private String userName;

    @Column(name = "password")
    private String password;

    @Nationalized
    @Column(name = "full_name")
    private String fullName;

    @Column(name = "email", length = 320)
    private String email;

    @Column(name = "phone", length = 20)
    private String phone;

    @Nationalized
    @Column(name = "avatar", length = 8000)
    private String avatar;

    @Column(name = "date_of_birth")
    private LocalDate dob;

    @Column(name = "state")
    private Boolean state;

    @Column(name = "status")
    private Boolean status;
}
