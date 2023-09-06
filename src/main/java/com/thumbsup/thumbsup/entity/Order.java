package com.thumbsup.thumbsup.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Order")
@Table(name = "tbl_order")
public class Order implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "date_created")
    private LocalDateTime dateCreated;

    @Column(name = "state")
    private Boolean state;

    @Column(name = "status")
    private Boolean status;

    @ManyToOne
    @JsonManagedReference
    @JoinColumn(name = "state_id")
    private State stateOrder;

    @ManyToOne
    @JsonManagedReference
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY)
    @JsonBackReference
    private List<OrderStore> orderStoreList;
}
