package com.thumbsup.thumbsup.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "OrderDetail")
@Table(name = "tbl_order_detail")
public class OrderDetail implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "original_price")
    private BigDecimal originalPrice;

    @Column(name = "discount")
    private BigDecimal discount;

    @Column(name = "sale_price")
    private BigDecimal salePrice;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "state")
    private Boolean state;

    @Column(name = "status")
    private Boolean status;

    @ManyToOne
    @JsonManagedReference
    @JoinColumn(name = "order_store_id")
    private OrderStore orderStore;

    @ManyToOne
    @JsonManagedReference
    @JoinColumn(name = "product_id")
    private Product product;
}
