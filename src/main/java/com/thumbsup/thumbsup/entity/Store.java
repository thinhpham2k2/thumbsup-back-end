package com.thumbsup.thumbsup.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Nationalized;

import java.math.BigDecimal;
import java.sql.Time;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Store")
@Table(name = "tbl_store", uniqueConstraints = {@UniqueConstraint(name = "store_user_name_unique", columnNames = "user_name"), @UniqueConstraint(name = "store_user_email_unique", columnNames = "email")})
public class Store {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "user_name", updatable = false, length = 50)
    private String userName;

    @Column(name = "password", length = 50)
    private String password;

    @Nationalized
    @Column(name = "store_name")
    private String storeName;

    @Column(name = "email", length = 320)
    private String email;

    @Column(name = "phone", length = 20)
    private String phone;

    @Nationalized
    @Column(name = "logo", length = 8000)
    private String logo;

    @Nationalized
    @Column(name = "cover_photo", length = 8000)
    private String coverPhoto;

    @Nationalized
    @Column(name = "address", length = 8000)
    private String address;

    @Column(name = "balance")
    private BigDecimal balance;

    @Column(name = "date_created")
    private LocalDate dateCreated;

    @Column(name = "opening_hours")
    private Time openingHours;

    @Column(name = "closing_hours")
    private Time closingHours;

    @Lob
    @Nationalized
    @Column(name = "description")
    private String description;

    @Column(name = "state")
    private Boolean state;

    @Column(name = "status")
    private Boolean status;

    @ManyToOne
    @JsonManagedReference
    @JoinColumn(name = "city_id")
    private City city;

    @OneToMany(mappedBy = "store", fetch = FetchType.LAZY)
    @JsonBackReference
    private List<Advertisement> adsList;

    @OneToMany(mappedBy = "store", fetch = FetchType.LAZY)
    @JsonBackReference
    private List<Product> productList;

    @OneToMany(mappedBy = "store", fetch = FetchType.LAZY)
    @JsonBackReference
    private List<WishlistStore> wishlistStoreList;
}
