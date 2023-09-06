package com.thumbsup.thumbsup.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "StateDetail")
@Table(name = "tbl_state_detail")
public class StateDetail implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "date")
    private LocalDateTime date;

    @Column(name = "status")
    private Boolean status;

    @ManyToOne
    @JsonManagedReference
    @JoinColumn(name = "state_id")
    private State state;

    @ManyToOne
    @JsonManagedReference
    @JoinColumn(name = "order_store_id")
    private OrderStore orderStore;
}
