package com.thumbsup.thumbsup.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Nationalized;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Customer")
@Table(name = "tbl_customer", uniqueConstraints = {@UniqueConstraint(name = "customer_user_name_unique", columnNames = "user_name"), @UniqueConstraint(name = "customer_user_email_unique", columnNames = "email")})
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "user_name", updatable = false, length = 50)
    private String userName;

    @Column(name = "password", length = 50)
    private String password;

    @Nationalized
    @Column(name = "full_name")
    private String fullName;

    @Column(name = "email", length = 320)
    private String email;

    @Column(name = "phone", length = 20)
    private String phone;

    @Lob
    @Nationalized
    @Column(name = "avatar")
    private String avatar;

    @Column(name = "date_of_birth")
    private LocalDate dob;

    @Lob
    @Nationalized
    @Column(name = "address")
    private String address;

    @Column(name = "state")
    private Boolean state;

    @Column(name = "status")
    private Boolean status;

    @ManyToOne
    @JsonManagedReference
    @JoinColumn(name = "city_id")
    private City city;

    @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY)
    @JsonBackReference
    private List<WishlistProduct> wishlistProductList;

    @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY)
    @JsonBackReference
    private List<WishlistStore> wishlistStoreList;

    @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY)
    @JsonBackReference
    private List<Review> reviewList;

    @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY)
    @JsonBackReference
    private List<Order> orderList;
}
