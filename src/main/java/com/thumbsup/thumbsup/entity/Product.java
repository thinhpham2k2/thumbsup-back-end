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
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Customer")
@Table(name = "tbl_customer")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @Nationalized
    @Column(name = "product_name")
    private String productName;

    @Column(name = "original_price")
    private BigDecimal originalPrice;

    @Column(name = "sale_price")
    private BigDecimal salePrice;

    @Column(name = "discount")
    private BigDecimal discount;

    @Column(name = "shelf_life")
    private BigDecimal shelfLife;

    @Column(name = "weight")
    private BigDecimal weight;

    @Column(name = "rating")
    private BigDecimal rating;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "num_of_rating")
    private Integer numOfRating;

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
    @JoinColumn(name = "store_id")
    private Store store;

    @ManyToOne
    @JsonManagedReference
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne
    @JsonManagedReference
    @JoinColumn(name = "brand_id")
    private Brand brand;

    @ManyToOne
    @JsonManagedReference
    @JoinColumn(name = "country_id")
    private Country country;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    @JsonBackReference
    private List<Image> imageList;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    @JsonBackReference
    private List<WishlistProduct> wishlistProductList;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    @JsonBackReference
    private List<Review> reviewList;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    @JsonBackReference
    private List<OrderDetail> orderDetailList;
}
