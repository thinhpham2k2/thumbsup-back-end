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
@Entity(name = "OrderStore")
@Table(name = "tbl_order_store")
public class OrderStore {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "shipping_fee")
    private BigDecimal shippingFee;

    @Column(name = "is_paid")
    private Boolean isPaid;

    @Lob
    @Nationalized
    @Column(name = "note", length = 8000)
    private String note;

    @Lob
    @Column(name = "code", length = 30)
    private String code;

    @Nationalized
    @Column(name = "address", length = 8000)
    private String address;

    @Column(name = "state")
    private Boolean state;

    @Column(name = "status")
    private Boolean status;

    @ManyToOne
    @JsonManagedReference
    @JoinColumn(name = "order_id")
    private Order order;

    @OneToMany(mappedBy = "orderStore", fetch = FetchType.LAZY)
    @JsonBackReference
    private List<StateDetail> stateDetailList;

    @OneToMany(mappedBy = "orderStore", fetch = FetchType.LAZY)
    @JsonBackReference
    private List<OrderDetail> orderDetailList;
}
